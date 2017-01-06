package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.dataparsing.Category;
import com.mycompany.myapp.repository.dataparsing.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CategoryService {
    @Inject
    private CategoryRepository categoryRepository;





    public List<Category> getCategoriesBelongsShop(Long idShop){
        return categoryRepository.findAllBelongsShop(idShop).stream()
            .filter(cat -> cat.getParent() == null)
            .collect(toList());
    }

    public List<Category> createNewCategories(List<Category> categories){
        return categoryRepository.save(categories);
    }

    @Transactional
    public List<Category> updateCategories(List<Category> categories){
        if ((categories == null) || categories.isEmpty()){
            return Collections.emptyList();
        }
        Long idShop = categories.get(0).getShop().getId();
        List<Category> deletedCategories = categoryRepository.findAllBelongsShop(idShop);
        categoryRepository.delete(deletedCategories);
        return createNewCategories(categories);
    }

    public List<Category> getCategoriesWithoutChildren(List<Category> categories){
        List<Category> categoriesWithoutChildren = new ArrayList<>();
        for (Category category : categories){
            if (category.getChildren() == null || category.getChildren().isEmpty()){
                categoriesWithoutChildren.add(category);
            } else{
                searchCategoryWithoutChildren(category.getChildren(), categoriesWithoutChildren);
            }
        }
        return categoriesWithoutChildren;
    }

    private void searchCategoryWithoutChildren(List<Category> categories, List<Category> categoriesWithoutChildren){
        for (Category category : categories){
            if (category.getChildren() == null || category.getChildren().isEmpty()){
                categoriesWithoutChildren.add(category);
            } else{
                searchCategoryWithoutChildren(category.getChildren(), categoriesWithoutChildren);
            }
        }
    }
}
