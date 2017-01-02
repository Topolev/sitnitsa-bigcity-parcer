package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.dataparsing.Category;
import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.repository.dataparsing.CategoryRepository;
import com.mycompany.myapp.repository.dataparsing.ShopRepository;

import com.mycompany.myapp.service.bigcity.CategoryFormatBigCity;
import com.mycompany.myapp.web.rest.vmbigcity.CategoryVM;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Inject
    ShopRepository shopRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Transactional
    public void saveCategories(List<CategoryVM> categories, Long shopId) {
        Shop shop = shopRepository.findOne(shopId);
        categories
            .forEach(cat ->{
                Category category = toCategory(cat, shop);
                categoryRepository.save(category);
            });
    }

    public void extractCategoriesBy(Long shopId){
        Shop shop = shopRepository.findOne(shopId);

    }

    private Category toCategory(CategoryVM categoryVM, Shop shop) {
        Category category = new Category();
        category.setStringId(categoryVM.getExternalId());
        category.setName(categoryVM.getName());
        category.setOriginUrl(categoryVM.getOriginalUrl());
        category.setParentId(categoryVM.getIdParent());
        category.setShop(shop);
        return category;
    }

    public List<CategoryFormatBigCity> convertToFormatBigCity(List<Category> categories){
        List<CategoryFormatBigCity> categoriesFormatBigCity = new ArrayList<>();
        for (Category category : categories){
            CategoryFormatBigCity categoryFormatBigCity = new CategoryFormatBigCity();
            categoryFormatBigCity.setExternalId(category.getStringId());
            categoryFormatBigCity.setName(category.getName());
            categoryFormatBigCity.setCategory(category);
            categoriesFormatBigCity.add(categoryFormatBigCity);
        }


        List<CategoryFormatBigCity> result = new ArrayList<>();
        for (CategoryFormatBigCity categoryFormatBigCity :categoriesFormatBigCity){
            Category category = categoryFormatBigCity.getCategory();
            String parentId = category.getParentId();
            if (parentId == null){
                result.add(categoryFormatBigCity);
            } else {
                categoriesFormatBigCity.stream()
                    .filter(cat -> cat.getExternalId().equals(parentId))
                    .findFirst()
                    .ifPresent(parent -> parent.addSubCategory(categoryFormatBigCity));
            }
        }

        return result;
    }



}
