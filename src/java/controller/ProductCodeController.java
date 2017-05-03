package controller;

import entity.ProductCode;
import entity.Product;
import facade.ProductCodeFacade;
import controller.util.MobilePageController;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "productCodeController")
@ViewScoped
public class ProductCodeController extends AbstractController<ProductCode> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isProductListEmpty;

    public ProductCodeController() {
        // Inform the Abstract parent controller of the concrete ProductCode Entity
        super(ProductCode.class);
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
        ProductCode selected = this.getSelected();
        if (selected != null) {
            ProductCodeFacade ejbFacade = (ProductCodeFacade) this.getFacade();
            this.isProductListEmpty = ejbFacade.isProductListEmpty(selected);
        } else {
            this.isProductListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Product entities that are
     * retrieved from ProductCode?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Product page
     */
    public String navigateProductList() {
        ProductCode selected = this.getSelected();
        if (selected != null) {
            ProductCodeFacade ejbFacade = (ProductCodeFacade) this.getFacade();
            List<Product> selectedProductList = ejbFacade.findProductList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Product_items", selectedProductList);
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/product/index";
    }

}
