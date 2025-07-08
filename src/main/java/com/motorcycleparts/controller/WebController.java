package com.motorcycleparts.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.motorcycleparts.entity.Customer;
import com.motorcycleparts.entity.Order;
import com.motorcycleparts.entity.Part;
import com.motorcycleparts.service.CustomerService;
import com.motorcycleparts.service.OrderService;
import com.motorcycleparts.service.PartService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class WebController {
    @Autowired
    private PartService partService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/parts")
    public String getParts(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Part> partPage = partService.getAllParts(pageable);
        model.addAttribute("parts", partPage.getContent());
        model.addAttribute("part", new Part());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", partPage.getTotalPages());
        return "parts";
    }

    @PostMapping("/parts/add")
    public String createPart(@Valid Part part, BindingResult result, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        if (result.hasErrors()) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Part> partPage = partService.getAllParts(pageable);
            model.addAttribute("parts", partPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", partPage.getTotalPages());
            return "parts";
        }
        partService.savePart(part); // Lưu Part, id sẽ được giữ nếu tồn tại
        return "redirect:/parts?page=" + page;
    }

    @GetMapping("/parts/edit/{id}")
    public String editPart(@PathVariable Long id, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Part part = partService.getPartById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid part ID"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Part> partPage = partService.getAllParts(pageable);
        model.addAttribute("part", part); // Gửi Part với id để điền vào form
        model.addAttribute("parts", partPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", partPage.getTotalPages());
        return "parts";
    }

    @GetMapping("/parts/delete/{id}")
    public String deletePart(@PathVariable Long id, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            partService.deletePart(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            Pageable pageable = PageRequest.of(page, size);
            Page<Part> partPage = partService.getAllParts(pageable);
            model.addAttribute("parts", partPage.getContent());
            model.addAttribute("part", new Part());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", partPage.getTotalPages());
            return "parts";
        }
        return "redirect:/parts?page=" + page;
    }

    @GetMapping("/parts/search")
    public String searchParts(@RequestParam(required = false) String name, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        if (name != null && !name.isEmpty()) {
            model.addAttribute("parts", partService.findByName(name));
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Part> partPage = partService.getAllParts(pageable);
            model.addAttribute("parts", partPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", partPage.getTotalPages());
        }
        model.addAttribute("part", new Part());
        return "parts";
    }

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("customer", new Customer());
        return "customers";
    }

    @PostMapping("/customers/add")
    public String createCustomer(@Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getAllCustomers());
            return "customers";
        }
        customerService.saveCustomer(customer); // Lưu Customer, id sẽ được giữ nếu tồn tại
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        model.addAttribute("customer", customer); // Gửi Customer với id để điền vào form
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, Model model) {
        try {
            customerService.deleteCustomer(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("customer", new Customer());
            return "customers";
        }
        return "redirect:/customers";
    }

    @GetMapping("/customers/search")
    public String searchCustomers(@RequestParam(required = false) String name, Model model) {
        if (name != null && !name.isEmpty()) {
            model.addAttribute("customers", customerService.findByName(name));
        } else {
            model.addAttribute("customers", customerService.getAllCustomers());
        }
        model.addAttribute("customer", new Customer());
        return "customers";
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("order", new Order());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("parts", partService.getAllParts(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
        return "orders";
    }

    @PostMapping("/orders/add")
    public String createOrder(@Valid Order order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("parts", partService.getAllParts(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
            return "orders";
        }
        try {
            orderService.saveOrder(order); // Lưu Order, id sẽ được giữ nếu tồn tại
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("parts", partService.getAllParts(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
            return "orders";
        }
        return "redirect:/orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        model.addAttribute("order", order); // Gửi Order với id để điền vào form
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("parts", partService.getAllParts(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
        return "orders";
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id, Model model) {
        try {
            orderService.deleteOrder(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("order", new Order());
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("parts", partService.getAllParts(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
            return "orders";
        }
        return "redirect:/orders";
    }

    @GetMapping("/orders/search")
    public String searchOrders(@RequestParam(required = false) Long customerId, Model model) {
        if (customerId != null) {
            model.addAttribute("orders", orderService.findByCustomerId(customerId));
        } else {
            model.addAttribute("orders", orderService.getAllOrders());
        }
        model.addAttribute("order", new Order());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("parts", partService.getAllParts(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
        return "orders";
    }

    @GetMapping("/report")
    public String getReport(Model model) {
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        model.addAttribute("revenueByCustomer", orderService.getRevenueByCustomer());
        return "report";
    }

    @GetMapping("/report/date")
    public String getReportByDate(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                  Model model) {
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        model.addAttribute("revenueByCustomer", orderService.getRevenueByCustomer());
        model.addAttribute("revenueByDate", orderService.getRevenueByDateRange(startDate, endDate));
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "report";
    }

    @GetMapping("/report/pdf")
    public void exportReportToPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Paragraph("Motorcycle Parts Management System - Sales Report"));
            document.add(new Paragraph("Total Revenue: " + orderService.getTotalRevenue()));
            document.add(new Paragraph("Revenue by Customer:"));
            PdfPTable table = new PdfPTable(2);
            table.addCell("Customer ID");
            table.addCell("Total Revenue");
            orderService.getRevenueByCustomer().forEach((id, revenue) -> {
                table.addCell(id.toString());
                table.addCell(revenue.toString());
            });
            document.add(table);
        } catch (DocumentException e) {
            throw new IOException("Error generating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }
}