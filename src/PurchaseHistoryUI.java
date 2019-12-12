import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PurchaseHistoryUI {

    public JFrame view;
    //public JList purchases;
    public JTable purchaseTable;

    public UserModel user = null;

//    public JButton btnLoad = new JButton("Load Customer");
//    public JButton btnSave = new JButton("Save Customer");
//
//    public JTextField txtCustomerID = new JTextField(20);
//    public JTextField txtName = new JTextField(20);
//    public JTextField txtPhone = new JTextField(20);
//    public JTextField txtAddress = new JTextField(20);


    public PurchaseHistoryUI(UserModel user) {
        this.user = user;
        PurchaseListModel list = null;
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("View Purchase History - Customer View");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Purchase history for " + user.mFullname);

        title.setFont (title.getFont().deriveFont (24.0f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        view.getContentPane().add(title);

        if(user.mUserType == 0) {
            list = StoreClient.getInstance().getDataAdapter().loadPurchaseHistory(user.mCustomerID);
        }
        else{
            list = StoreClient.getInstance().getDataAdapter().viewSummary(user.mCustomerID);
            System.out.println(list);
        }

        DefaultTableModel tableData = new DefaultTableModel();

        tableData.addColumn("PurchaseID");
        tableData.addColumn("ProductID");
        tableData.addColumn("Product Name");
        tableData.addColumn("Total");

        for (PurchaseModel purchase : list.purchases) {
            Object[] row = new Object[tableData.getColumnCount()];
            row[0] = purchase.mPurchaseID;
            row[1] = purchase.mProductID;
            ProductModel product = StoreClient.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
            row[2] = product.mName;
            row[3] = purchase.mTotal;
            tableData.addRow(row);
        }

//        purchases = new JList(data);
        purchaseTable = new JTable(tableData);
        JScrollPane scrollableList = new JScrollPane(purchaseTable);
        view.getContentPane().add(scrollableList);
    }
}