package ir.maktab;

import ir.maktab.Exception.*;
import ir.maktab.model.Manager;
import ir.maktab.model.Order;
import ir.maktab.model.Product;
import ir.maktab.model.User;
import ir.maktab.model.enumeration.Gender;
import ir.maktab.model.enumeration.OrderStatus;
import ir.maktab.model.enumeration.ProductType;
import ir.maktab.service.OrderService;
import ir.maktab.service.ProductService;
import ir.maktab.service.UserService;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    static Manager manager = new Manager();
    static ProductService productService = new ProductService();
    static UserService userService = new UserService();
    static OrderService orderService = new OrderService();
    static Validation validation = new Validation();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try {
            System.out.println("Choose your role : 1)manager 2)user");
            Integer choice = input.nextInt();
            validation.validateInput(String.valueOf(choice));
            switch (choice) {
                case (1):
                    boolean repeat = false;
                    do {
                        System.out.println("enter username:");
                        String username = input.next();
                        System.out.println("enter password:");
                        String password = input.next();
                        if (username.equals(manager.getUsername()) && password.equals(manager.getPassword())) {
                            repeat = true;
                            System.out.println("welcome");
                            System.out.println("enter product info : product type,product name,price,count");
                            String information = input.next();
                            addProduct(information);
                        } else {
                            System.out.println("username or password is wrong! TRY AGAIN ");
                        }
                    } while (!repeat);
                    break;
                case 2:
                    System.out.println("enter your phone number:");
                    String username = input.next();
                    try {
                        validation.validatePhoneNumber(username);
                        Boolean usernameExist = userService.isUserExist(username);
                        if (usernameExist) {
                            userActions(username);
                        } else {
                            System.out.println("enter your info :fullName,email,gender,birthDate,nationalId");
                            String information = input.next();
                            addUser(information, username);
                            userActions(username);
                        }
                        break;
                    } catch (ParseException | InvalidPhoneNumberException e) {
                        System.out.println(e.getMessage());
                    }
            }
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void userActions(String phoneNumber) throws SQLException, ClassNotFoundException {
        Boolean exit = false;
        do {
            System.out.println("product list");
            System.out.println(productService.findAllProduct());
            System.out.println("1)add product to your cart \n2)delete product from your cart " +
                    "\n3)View Cart Products \n4)view Cart item prices \n5)Final purchase confirmation \n6)Exit");
            Integer choice = input.nextInt();
            try {
                validation.validateInput(String.valueOf(choice));
            } catch (InvalidInputException e) {
                e.printStackTrace();
            }
            switch (choice) {
                case 1:
                    addProductToCart(phoneNumber);
                    break;
                case 2:
                    deleteProductFromCart(phoneNumber);
                    break;
                case 3:
                    showAllCartsItems(phoneNumber);
                    break;
                case 4:
                    finalCartItemsPrice(phoneNumber);
                    break;
                case 5:
                    finalizeThePurchase(phoneNumber);
                    break;
                case 6:
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    private static void showAllCartsItems(String phoneNumber) {
        User user = userService.findByPhoneNumber(phoneNumber);
        Integer userId = user.getId();
        List<Order> reservedOrderOfUser = orderService.findReservedOrderOfUser(user);
        System.out.println(reservedOrderOfUser);
        System.out.println("**************************************************");
    }

    private static void finalizeThePurchase(String phoneNumber) {
        System.out.println("Do you want to finalize the purchase? 1)yes 2)no");
        Integer choice = input.nextInt();
        switch (choice) {
            case 1:
                User user = userService.findByPhoneNumber(phoneNumber);
                finalCartItemsPrice(phoneNumber);
                List<Order> reservedOrderOfUser = orderService.findReservedOrderOfUser(user);
                for (Order order : reservedOrderOfUser) {
                    order.setOrderStatus(OrderStatus.PAID);
                    orderService.update(order);
                }
                System.out.println("Your request has been submitted.");
                break;
            case 2:
                break;
        }
    }

    private static void finalCartItemsPrice(String phoneNumber) {
        User userByPhoneNumber = userService.findByPhoneNumber(phoneNumber);
        List<Long> prices = orderService.calculateFinalPriceOfUserOrders(userByPhoneNumber);
        Long[] price = prices.toArray(new Long[0]);
        int sum = 0;
        for (Long eachPrice : price) {
            sum += eachPrice;
        }
        System.out.println("The price of shopping cart items = " + sum);
    }

    private static void deleteProductFromCart(String phoneNumber) {
        System.out.println("your orders:");
        User user = userService.findByPhoneNumber(phoneNumber);
        List<Order> orders = orderService.findReservedOrderOfUser(user);
        System.out.println(orders);
        System.out.println("Enter the product ID to delete from your cart:");
        Integer productId = input.nextInt();
        Product product = productService.findById(productId);
        Order order = orderService.orderedThisProduct(user, product);
        Integer count = order.getCount();
        Integer newCount = product.getCount() + count;
        order.setOrderStatus(OrderStatus.CANCELED);
        orderService.update(order);
        product.setCount(newCount);
        productService.update(product);
    }

    private static void addProductToCart(String phoneNumber) {
        System.out.println("Enter the product ID to add to your cart:");
        Integer productId = input.nextInt();
        System.out.println("Enter the number you want:");
        Integer numberOfProductOrder = input.nextInt();
        User user = userService.findByPhoneNumber(phoneNumber);
        Product product = productService.findById(productId);
        Integer productCount = product.getCount();
        Boolean addToExistOrder = orderService.isThisUserOrderedThisProduct(user, product);
        if (addToExistOrder) {
            if (product.getCount() > 0) {
                Order order = orderService.orderedThisProduct(user, product);
                Integer newOrderCount = (order.getCount()) + numberOfProductOrder;
                order.setCount(newOrderCount);
                orderService.update(order);
                Integer newCount = productCount - numberOfProductOrder;
                product.setCount(newCount);
                productService.update(product);
            } else {
                System.out.println("Inventory is not enough");
            }
        } else {
            int numberOfOrders = 0;
            if (orderService.isUserHaveOrder(user)) {
                List<Order> orders = orderService.findReservedOrderOfUser(user);
                numberOfOrders = orders.size();
            }
            if (numberOfProductOrder <= productCount && numberOfOrders < 5) {
                Integer newCount = productCount - numberOfProductOrder;
                product.setCount(newCount);
                productService.update(product);
                OrderStatus orderStatus = OrderStatus.RESERVED;
                Order newOrder = new Order();
                newOrder.setUser(user);
                newOrder.setProduct(product);
                newOrder.setCount(numberOfProductOrder);
                newOrder.setOrderStatus(orderStatus);
                orderService.save(newOrder);
            } else {
                System.out.println("You can not select this product.");
            }
        }
    }

    private static void addUser(String information, String phoneNumber) throws ParseException {
        String[] arrOfInfo = information.split(",", 5);
        String fullName = arrOfInfo[0];
        String email = arrOfInfo[1];
        Gender gender = Gender.getVal(arrOfInfo[2].toUpperCase());
        String dateOfBirth = arrOfInfo[3];
        Date parseDateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        String nationalId = arrOfInfo[4];
        try {
            if (validation.validateUserInfo(fullName, email, nationalId)) {
                User user = new User();
                user.setFullName(fullName);
                user.setPhoneNumber(phoneNumber);
                user.setEmail(email);
                user.setGender(gender);
                user.setBirthDate(parseDateOfBirth);
                user.setNationalId(nationalId);

                userService.save(user);
            }
        } catch (InvalidEmailException | InvalidNationalIDException | InvalidNameException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addProduct(String information) throws SQLException {
        String[] arrOfInfo = information.split(",", 4);
        ProductType productType = ProductType.getVal(arrOfInfo[0].toUpperCase());
        String productName = arrOfInfo[1];
        Long price = Long.parseLong(arrOfInfo[2]);
        Integer count = Integer.parseInt(arrOfInfo[3]);

        Product product = new Product();
        product.setProductType(productType);
        product.setName(productName);
        product.setPrice(price);
        product.setCount(count);

        productService.save(product);

    }
}