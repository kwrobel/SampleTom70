package controller;

import entity.Product;
import entity.Warehouse;
import entity.PurchaseOrder;
import facade.ProductFacade;
import controller.util.MobilePageController;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "productController")
@ViewScoped
public class ProductController extends AbstractController<Product> {

    @Inject
    private ProductCodeController productCodeController;
    @Inject
    private ManufacturerController manufacturerIdController;
    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isWarehouseListEmpty;
    private boolean isPurchaseOrderListEmpty;

    public ProductController() {
        // Inform the Abstract parent controller of the concrete Product Entity
        super(Product.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        productCodeController.setSelected(null);
        manufacturerIdController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsWarehouseListEmpty();
        this.setIsPurchaseOrderListEmpty();
    }

    public boolean getIsWarehouseListEmpty() {
        return this.isWarehouseListEmpty;
    }

    private void setIsWarehouseListEmpty() {
        Product selected = this.getSelected();
        if (selected != null) {
            ProductFacade ejbFacade = (ProductFacade) this.getFacade();
            this.isWarehouseListEmpty = (selected.getWarehouseList() == null || selected.getWarehouseList().isEmpty());
        } else {
            this.isWarehouseListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Warehouse entities that
     * are retrieved from Product and returns the navigation outcome.
     *
     * @return navigation outcome for Warehouse page
     */
    public String navigateWarehouseList() {
        Product selected = this.getSelected();
        if (selected != null) {
            ProductFacade ejbFacade = (ProductFacade) this.getFacade();
            // Note: WarehouseList has already been read as is initialized
            List<Warehouse> selectedWarehouseList = selected.getWarehouseList();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Warehouse_items", selectedWarehouseList);
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/warehouse/index";
    }

    /**
     * Sets the "selected" attribute of the ProductCode controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareProductCode(ActionEvent event) {
        Product selected = this.getSelected();
        if (selected != null && productCodeController.getSelected() == null) {
            productCodeController.setSelected(selected.getProductCode());
        }
    }

    /**
     * Sets the "selected" attribute of the Manufacturer controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareManufacturerId(ActionEvent event) {
        Product selected = this.getSelected();
        if (selected != null && manufacturerIdController.getSelected() == null) {
            manufacturerIdController.setSelected(selected.getManufacturerId());
        }
    }

    public boolean getIsPurchaseOrderListEmpty() {
        return this.isPurchaseOrderListEmpty;
    }

    private void setIsPurchaseOrderListEmpty() {
        Product selected = this.getSelected();
        if (selected != null) {
            ProductFacade ejbFacade = (ProductFacade) this.getFacade();
            this.isPurchaseOrderListEmpty = ejbFacade.isPurchaseOrderListEmpty(selected);
        } else {
            this.isPurchaseOrderListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of PurchaseOrder entities
     * that are retrieved from Product and returns the navigation outcome.
     *
     * @return navigation outcome for PurchaseOrder page
     */
    public String navigatePurchaseOrderList() {
        Product selected = this.getSelected();
        if (selected != null) {
            ProductFacade ejbFacade = (ProductFacade) this.getFacade();
            List<PurchaseOrder> selectedPurchaseOrderList = ejbFacade.findPurchaseOrderList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("PurchaseOrder_items", selectedPurchaseOrderList);
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/purchaseOrder/index";
    }

}
