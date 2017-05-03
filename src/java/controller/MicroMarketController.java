package controller;

import entity.MicroMarket;
import entity.Customer;
import facade.MicroMarketFacade;
import controller.util.MobilePageController;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "microMarketController")
@ViewScoped
public class MicroMarketController extends AbstractController<MicroMarket> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isCustomerListEmpty;

    public MicroMarketController() {
        // Inform the Abstract parent controller of the concrete MicroMarket Entity
        super(MicroMarket.class);
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
        MicroMarket selected = this.getSelected();
        if (selected != null) {
            MicroMarketFacade ejbFacade = (MicroMarketFacade) this.getFacade();
            this.isCustomerListEmpty = ejbFacade.isCustomerListEmpty(selected);
        } else {
            this.isCustomerListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Customer entities that
     * are retrieved from MicroMarket and returns the navigation outcome.
     *
     * @return navigation outcome for Customer page
     */
    public String navigateCustomerList() {
        MicroMarket selected = this.getSelected();
        if (selected != null) {
            MicroMarketFacade ejbFacade = (MicroMarketFacade) this.getFacade();
            List<Customer> selectedCustomerList = ejbFacade.findCustomerList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Customer_items", selectedCustomerList);
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/customer/index";
    }

}
