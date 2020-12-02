package com.company.sampleandroidproject.viewmodel.supplier;

import android.app.Application;
import android.os.Parcelable;

import com.company.sampleandroidproject.viewmodel.EntityViewModel;
import com.sap.cloud.android.odata.espmcontainer.Supplier;
import com.sap.cloud.android.odata.espmcontainer.ESPMContainerMetadata.EntitySets;

/*
 * Represents View model for Supplier
 * Having an entity view model for each <T> allows the ViewModelProvider to cache and
 * return the view model of that type. This is because the ViewModelStore of
 * ViewModelProvider cannot not be able to tell the difference between EntityViewModel<type1>
 * and EntityViewModel<type2>.
 */
public class SupplierViewModel extends EntityViewModel<Supplier> {

    /**
    * Default constructor for a specific view model.
    * @param application - parent application
    */
    public SupplierViewModel(Application application) {
        super(application, EntitySets.suppliers, Supplier.city);
    }

    /**
    * Constructor for a specific view model with navigation data.
    * @param application - parent application
    * @param navigationPropertyName - name of the navigation property
    * @param entityData - parent entity (starting point of the navigation)
    */
	 public SupplierViewModel(Application application, String navigationPropertyName, Parcelable entityData) {
        super(application, EntitySets.suppliers, Supplier.city, navigationPropertyName, entityData);
    }
}
