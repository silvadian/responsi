package kuispbo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

class DataBuku extends JFrame {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/kuis";
    static final String USER = "root";
    static final String PASS = "";
    
    Connection koneksi;
    Statement statement;  
    
    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;
      
    
    JLabel lTitle = new JLabel("Selamat datang admin, silahkan masuk ke menu tambah untuk masukkan data baru,klik petunjuk jika mengalami kesulitan");
    
    
    public DataBuku(){
        try{
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }        

        setTitle("HOME");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setSize(660, 585);
        setLocation(630, 300);

       
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);        
        
        
        
        add(lTitle);
        lTitle.setBounds(1, 190, 900, 50);
        lTitle.setFont(new Font("Segoe Script",Font.BOLD, 9));
        lTitle.setForeground(Color.red);
        
        



    String[][] readBuku() {
        try{
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][5];
            String query = "Select * from `buku`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("Kode_buku");
                data[jmlData][1] = resultSet.getString("Judul");
                data[jmlData][2] = resultSet.getString("Pengarang");
                data[jmlData][3] = resultSet.getString("Penerbit");
                data[jmlData][4] = resultSet.getString("Tahun");
                
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }

    int getBanyakData() {
        int jmlData = 0;
        try{
            statement = koneksi.createStatement();
            String query = "SELECT * from `buku`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                jmlData++;
            }
            return jmlData;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return 0;
        }
    }
    int getBanyakDataSearch(String getSearch){
        int jmlData=0;
        try{
            statement = koneksi.createStatement();
            String query = "Select * from `buku` where `Judul` like '%"+getSearch+"%'";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
            jmlData++;
            } return jmlData;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL ERROR");
             return 0;
        }
    }
    String[][] searchBuku(String getSearch){
        try{
            int jmlData = 0;
            String data[][] = new String[getBanyakDataSearch(getSearch)][5];
            String query = "Select * from `buku` where `Judul` like '%"+getSearch+"%'";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("Kode_buku");
                data[jmlData][1] = resultSet.getString("Judul");
                data[jmlData][2] = resultSet.getString("Pengarang");
                data[jmlData][3] = resultSet.getString("Penerbit");
                data[jmlData][4] = resultSet.getString("Tahun");
            jmlData++;
            } return data;
        } catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL ERROR");
            return null;
        }
    }
  
    void hapusBuku(String kode) {
        try{
            String query = "DELETE FROM `buku` WHERE `Kode_buku` = '"+kode+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil menghapus kode "+kode );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }
}

  
