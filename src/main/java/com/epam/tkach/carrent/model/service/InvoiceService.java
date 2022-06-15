package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.exceptions.InvoiceRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.controller.exceptions.TransactionException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.Invoice;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.entity.Transaction;
import com.epam.tkach.carrent.model.entity.enums.InvoiceTypes;
import com.epam.tkach.carrent.model.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.model.repository.InvoiceRepoI;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class InvoiceService {
    private static final Logger logger = LogManager.getLogger(InvoiceService.class);

    public static int getCountInDB(LinkedHashMap<String, Object> filters) throws InvoiceRepoException {
        InvoiceRepoI repo = RepositoryFactory.getInvoiceRepo();
        return repo.getCountInDB(filters);
    }

    public static List<Invoice> getList(int currentPage, int recordsPerPage, LinkedHashMap<String, Object> filters, String orderBy) throws InvoiceRepoException {
        InvoiceRepoI repo = RepositoryFactory.getInvoiceRepo();
        return repo.getListForPagination(currentPage, recordsPerPage,filters, orderBy);
    }

    public static void createNewAndSaveInDB(int orderID, InvoiceTypes invoiceType, double amount, String damage) throws OrderRepoException, InvoiceRepoException {
        Invoice invoice = createNew(orderID, invoiceType,amount, damage);
        addNew(invoice);
    }
    public static Invoice createNew(int orderID, InvoiceTypes invoiceType, double amount, String damage) throws OrderRepoException, InvoiceRepoException {
        Invoice invoice = new Invoice();
        Order order = OrderService.findById(orderID);
        invoice.setOrder(order);
        invoice.setType(invoiceType);
        invoice.setClient(order.getClient());
        if (invoiceType==InvoiceTypes.RENT){
            invoice.setDescription("For car rent by order №" + order.getID());
            invoice.setAmount(order.getRentSum());
        }else {
            invoice.setDescription("Order №" + order.getID() +damage);
            invoice.setAmount(amount);
        }
        invoice.setPaid(false);
        return invoice;
    }
    public static void addNew(Invoice invoice) throws InvoiceRepoException{
        InvoiceRepoI repo = RepositoryFactory.getInvoiceRepo();
        repo.addNew(invoice);
    }
    public static void pay(int invoiceId) throws InvoiceRepoException, TransactionException, OrderRepoException, SQLException {
        InvoiceRepoI repo = RepositoryFactory.getInvoiceRepo();
        Invoice invoice = findByID(invoiceId);
        if (checkPayment(invoice.getClient().getID(),invoice.getAmount())==false) throw new InvoiceRepoException(Messages.ERROR_LOW_BALANCE);

        //Starting all in transaction
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        con.setAutoCommit(false);
        //do payment
        //1. Adding transaction
        Transaction transaction = TransactionService.createPayment(invoice.getClient(),invoiceId,invoice.getAmount());
        TransactionService.addNew(transaction, con);
        //2. Setting order status
        Order order = invoice.getOrder();
        order.setStatus(OrderStatuses.INPROGRESS);
        OrderService.update(order, con);
        //3. Setting paid to invoice
        invoice.setPaid(true);
        InvoiceService.update(invoice, con);
        con.commit();
        connectionPool.close(con, null,  null);

    }

    public static Invoice findByID(int id) throws InvoiceRepoException {
        InvoiceRepoI repo = RepositoryFactory.getInvoiceRepo();
        Optional<Invoice> opt = repo.getById(id);
        if (opt.isEmpty()) throw new InvoiceRepoException();
        return opt.get();
    }
    public static void update(Invoice invoice) throws InvoiceRepoException{
        InvoiceRepoI repo = RepositoryFactory.getInvoiceRepo();
        repo.update(invoice, null);
    }

    public static void update(Invoice invoice, Connection con) throws InvoiceRepoException{
        InvoiceRepoI repo = RepositoryFactory.getInvoiceRepo();
        repo.update(invoice, con);
    }

    public static boolean checkPayment(int userId, double amount){
        try {
            double balance = TransactionService.getUserBalance(userId);
            return balance>amount;
        } catch (TransactionException e) {
            logger.error(e);
            return false;
        }
    }

    public static List<String> getSortingList(){
        List<String> sortList = new ArrayList<>();
        sortList.add("amount.up");
        sortList.add("amount.down");
        sortList.add("date.up");
        sortList.add("date.down");
        sortList.add("paid.up");
        sortList.add("paid.down");
        return sortList;
    }

    /**
     * Function return column name for SQL query
     * @param sortName - name of sorting param. All list in getSortingList
     * @return
     */
    public static String getColumnNameToSort(String sortName){
        if (sortName==null) return "";
        switch (sortName){
            case "amount.down": return "invoices.amount desc";
            case "amount.up": return "invoices.amount ASC";
            case "date.up": return "invoices.timestamp ASC";
            case "date.down": return "invoices.timestamp desc";
            case "paid.up": return "invoices.paid ASC";
            case "paid.down": return "invoices.paid desc";
            default: return "";
        }
    }
}
