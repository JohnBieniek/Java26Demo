package com.JohnBieniek.Java26Demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.model.Item;
import com.JohnBieniek.Java26Demo.repository.ItemRepository;

@RestController
@RequestMapping(value = "/demo")
public class SqlController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private final ItemRepository itemRepository;

    public SqlController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoInMemorySqlRepository")
    public String demoInMemorySqlRepository(String itemName) {
        Item saved = itemRepository.save(new Item(itemName));
        List<Item> items = itemRepository.findAll();
        logger.info("Saved item: {} and total items: {}", saved, items.size());
        return "Saved item: " + saved + " | Total items: " + items.size() + " | All items: " + items;
    }
}