package controller;

import entity.Warehouse;
import entity.Product;
import facade.WarehouseFacade;
import controller.util.MobilePageController;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "warehouseController")
@ViewScoped
public class WarehouseController extends AbstractController<Warehouse> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isProductListEmpty;

    public WarehouseController() {
        // Inform the Abstract parent controller of the concrete Warehouse Entity
        super(Warehouse.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsProductListEmpty();
    }

    public boolean getIsProductListEmpty() {
        return this.isProductListEmpty;
    }

    private void setIsProductListEmpty() {
        Warehouse selected = this.getSelected();
        if (selected != null) {
            WarehouseFacade ejbFacade = (WarehouseFacade) this.getFacade();
            this.isProductListEmpty = ejbFacade.isProductListEmpty(selected);
        } else {
            this.isProductListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Product entities that are
     * retrieved from Warehouse and returns the navigation outcome.
     *
     * @return navigation outcome for Product page
     */
    public String navigateProductList() {
        Warehouse selected = this.getSelected();
        if (selected != null) {
            WarehouseFacade ejbFacade = (WarehouseFacade) this.getFacade();
            List<Product> selectedProductList = ejbFacade.findProductList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Product_items", selectedProductList);
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/product/index";
    }

}
