package controller;

import entity.DiscountCode;
import entity.Customer;
import facade.DiscountCodeFacade;
import controller.util.MobilePageController;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "discountCodeController")
@ViewScoped
public class DiscountCodeController extends AbstractController<DiscountCode> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isCustomerListEmpty;

    public DiscountCodeController() {
        // Inform the Abstract parent controller of the concrete DiscountCode Entity
        super(DiscountCode.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsCustomerListEmpty();
    }

    public boolean getIsCustomerListEmpty() {
        return this.isCustomerListEmpty;
    }

    private void setIsCustomerListEmpty() {
        DiscountCode selected = this.getSelected();
        if (selected != null) {
            DiscountCodeFacade ejbFacade = (DiscountCodeFacade) this.getFacade();
            this.isCustomerListEmpty = ejbFacade.isCustomerListEmpty(selected);
        } else {
            this.isCustomerListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Customer entities that
     * are retrieved from DiscountCode?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Customer page
     */
    public String navigateCustomerList() {
        DiscountCode selected = this.getSelected();
        if (selected != null) {
            DiscountCodeFacade ejbFacade = (DiscountCodeFacade) this.getFacade();
            List<Customer> selectedCustomerList = ejbFacade.findCustomerList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Customer_items", selectedCustomerList);
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/customer/index";
    }

}
