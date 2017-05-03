package controller;

import entity.Manufacturer;
import entity.Product;
import facade.ManufacturerFacade;
import controller.util.MobilePageController;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "manufacturerController")
@ViewScoped
public class ManufacturerController extends AbstractController<Manufacturer> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isProductListEmpty;

    public ManufacturerController() {
        // Inform the Abstract parent controller of the concrete Manufacturer Entity
        super(Manufacturer.class);
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
        Manufacturer selected = this.getSelected();
        if (selected != null) {
            ManufacturerFacade ejbFacade = (ManufacturerFacade) this.getFacade();
            this.isProductListEmpty = ejbFacade.isProductListEmpty(selected);
        } else {
            this.isProductListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Product entities that are
     * retrieved from Manufacturer?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Product page
     */
    public String navigateProductList() {
        Manufacturer selected = this.getSelected();
        if (selected != null) {
            ManufacturerFacade ejbFacade = (ManufacturerFacade) this.getFacade();
            List<Product> selectedProductList = ejbFacade.findProductList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Product_items", selectedProductList);
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/product/index";
    }

}
