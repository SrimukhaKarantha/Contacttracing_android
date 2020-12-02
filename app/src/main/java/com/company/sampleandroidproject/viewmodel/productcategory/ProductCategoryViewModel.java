package com.company.sampleandroidproject.viewmodel.productcategory;

import android.app.Application;
import android.os.Parcelable;

import com.company.sampleandroidproject.viewmodel.EntityViewModel;
import com.sap.cloud.android.odata.espmcontainer.ProductCategory;
import com.sap.cloud.android.odata.espmcontainer.ESPMContainerMetadata.EntitySets;

/*
 * Represents View model for ProductCategory
 * Having an entity view model for each <T> allows the ViewModelProvider to cache and
 * return the view model of that type. This is because the ViewModelStore of
 * ViewModelProvider cannot not be able to tell the difference between EntityViewModel<type1>
 * and EntityViewModel<type2>.
 */
public class ProductCategoryViewModel extends EntityViewModel<ProductCategory> {

    /**
    * Default constructor for a specific view model.
    * @param application - parent application
    */
    public ProductCategoryViewModel(Application application) {
        super(application, EntitySets.productCategories, ProductCategory.categoryName);
    }

    /**
    * Constructor for a specific view model with navigation data.
    * @param application - parent application
    * @param navigationPropertyName - name of the navigation property
    * @param entityData - parent entity (starting point of the navigation)
    */
	 public ProductCategoryViewModel(Application application, String navigationPropertyName, Parcelable entityData) {
        super(application, EntitySets.productCategories, ProductCategory.categoryName, navigationPropertyName, entityData);
    }
}
