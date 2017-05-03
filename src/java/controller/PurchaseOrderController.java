package controller;

import entity.PurchaseOrder;
import facade.PurchaseOrderFacade;
import controller.util.MobilePageController;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "purchaseOrderController")
@ViewScoped
public class PurchaseOrderController extends AbstractController<PurchaseOrder> {

    @Inject
    private ProductController productIdController;
    @Inject
    private CustomerController customerIdController;
    @Inject
    private MobilePageController mobilePageController;

    public PurchaseOrderController() {
        // Inform the Abstract parent controller of the concrete PurchaseOrder Entity
        super(PurchaseOrder.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        productIdController.setSelected(null);
        customerIdController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Product controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareProductId(ActionEvent event) {
        PurchaseOrder selected = this.getSelected();
        if (selected != null && productIdController.getSelected() == null) {
            productIdController.setSelected(selected.getProductId());
        }
    }

    /**
     * Sets the "selected" attribute of the Customer controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareCustomerId(ActionEvent event) {
        PurchaseOrder selected = this.getSelected();
        if (selected != null && customerIdController.getSelected() == null) {
            customerIdController.setSelected(selected.getCustomerId());
        }
    }

}
