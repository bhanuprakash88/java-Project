// Source code is decompiled from a .class file using FernFlower decompiler.
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFxDatabase extends Application {
   String url = "jdbc:mysql://localhost:3306/AttendanceSystem";
   String user = "root";
   String password = "bhanu2004";

   public JavaFxDatabase() {
   }

   public static void main(String[] var0) {
      launch(var0);
   }

   public void start(Stage var1) {
      Label var2 = new Label("SQL CRUD Operations");
      Button var3 = new Button("Create Table");
      Button var4 = new Button("Insert Values");
      Button var5 = new Button("Update Table");
      Button var6 = new Button("Delete Table");
      Button var7 = new Button("Truncate Table");
      Button var8 = new Button("Drop Table");
      var3.setOnAction((var1x) -> {
         this.showCreateTableWindow();
      });
      var4.setOnAction((var1x) -> {
         this.showInsertWindow();
      });
      var5.setOnAction((var1x) -> {
         this.showUpdateWindow();
      });
      var6.setOnAction((var1x) -> {
         this.showSimpleTableActionWindow("Delete", "DELETE FROM ");
      });
      var7.setOnAction((var1x) -> {
         this.showSimpleTableActionWindow("Truncate", "TRUNCATE TABLE ");
      });
      var8.setOnAction((var1x) -> {
         this.showSimpleTableActionWindow("Drop", "DROP TABLE ");
      });
      VBox var9 = new VBox(15.0, new Node[]{var2, var3, var4, var5, var6, var7, var8});
      var9.setStyle("-fx-padding: 30; -fx-alignment: center;");
      Scene var10 = new Scene(var9, 400.0, 400.0);
      var1.setScene(var10);
      var1.setTitle("JavaFX MySQL Manager");
      var1.show();
   }

   private void showCreateTableWindow() {
      Stage var1 = new Stage();
      Label var2 = new Label("Enter Table Name:");
      TextField var3 = new TextField();
      Button var4 = new Button("Submit");
      VBox var5 = new VBox(10.0, new Node[]{var2, var3, var4});
      var5.setStyle("-fx-padding: 20; -fx-alignment: center;");
      Scene var6 = new Scene(var5, 300.0, 200.0);
      var1.setScene(var6);
      var1.setTitle("Table Name");
      var1.show();
      var4.setOnAction((var3x) -> {
         String var4 = var3.getText().trim();
         if (var4.isEmpty()) {
            this.showAlert(AlertType.ERROR, "Table name cannot be empty!");
         } else {
            try {
               Connection var5 = DriverManager.getConnection(this.url, this.user, this.password);

               try {
                  DatabaseMetaData var6 = var5.getMetaData();
                  ResultSet var7 = var6.getTables((String)null, (String)null, var4, (String[])null);
                  if (var7.next()) {
                     this.showAlert(AlertType.WARNING, "Table already exists!");
                  } else {
                     var1.close();
                     this.showFieldCountWindow(var4);
                  }
               } catch (Throwable var9) {
                  if (var5 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                     }
                  }

                  throw var9;
               }

               if (var5 != null) {
                  var5.close();
               }
            } catch (SQLException var10) {
               this.showAlert(AlertType.ERROR, "Error: " + var10.getMessage());
            }

         }
      });
   }

   private void showFieldCountWindow(String var1) {
      Stage var2 = new Stage();
      Label var3 = new Label("Enter Number of Fields:");
      TextField var4 = new TextField();
      Button var5 = new Button("Next");
      VBox var6 = new VBox(10.0, new Node[]{var3, var4, var5});
      var6.setStyle("-fx-padding: 20; -fx-alignment: center;");
      Scene var7 = new Scene(var6, 300.0, 200.0);
      var2.setScene(var7);
      var2.setTitle("Field Count");
      var2.show();
      var5.setOnAction((var4x) -> {
         int var5;
         try {
            var5 = Integer.parseInt(var4.getText().trim());
            if (var5 <= 0) {
               throw new NumberFormatException();
            }
         } catch (NumberFormatException var7) {
            this.showAlert(AlertType.ERROR, "Enter a valid number of fields.");
            return;
         }

         var2.close();
         this.getFieldDetails(var1, var5);
      });
   }

   private void showInsertWindow() {
      Stage var1 = new Stage();
      TextField var2 = new TextField();
      Button var3 = new Button("Next");
      VBox var4 = new VBox(10.0, new Node[]{new Label("Enter Table Name:"), var2, var3});
      var4.setStyle("-fx-padding: 20; -fx-alignment: center;");
      var3.setOnAction((var2x) -> {
         String var3 = var2.getText().trim();

         try {
            Connection var4 = DriverManager.getConnection(this.url, this.user, this.password);

            label68: {
               try {
                  ResultSet var5 = var4.getMetaData().getColumns((String)null, (String)null, var3, (String)null);
                  ArrayList var6 = new ArrayList();

                  while(var5.next()) {
                     var6.add(var5.getString("COLUMN_NAME"));
                  }

                  if (!var6.isEmpty()) {
                     Stage var7 = new Stage();
                     VBox var8 = new VBox(10.0);
                     var8.setStyle("-fx-padding: 20; -fx-alignment: center;");
                     ArrayList var9 = new ArrayList();
                     Iterator var10 = var6.iterator();

                     while(var10.hasNext()) {
                        String var11 = (String)var10.next();
                        TextField var12 = new TextField();
                        var8.getChildren().addAll(new Node[]{new Label("Enter value for " + var11 + ":"), var12});
                        var9.add(var12);
                     }

                     Button var16 = new Button("Insert");
                     var8.getChildren().add(var16);
                     var16.setOnAction((var4x) -> {
                        try {
                           Connection var5 = DriverManager.getConnection(this.url, this.user, this.password);

                           try {
                              StringBuilder var6 = new StringBuilder("INSERT INTO " + var3 + " VALUES (");

                              for(int var7x = 0; var7x < var9.size(); ++var7x) {
                                 var6.append("?");
                                 if (var7x != var9.size() - 1) {
                                    var6.append(", ");
                                 }
                              }

                              var6.append(")");
                              PreparedStatement var12 = var5.prepareStatement(var6.toString());
                              int var8 = 0;

                              while(true) {
                                 if (var8 >= var9.size()) {
                                    var12.executeUpdate();
                                    this.showAlert(AlertType.INFORMATION, "Values inserted successfully!");
                                    var7.close();
                                    break;
                                 }

                                 var12.setString(var8 + 1, ((TextField)var9.get(var8)).getText().trim());
                                 ++var8;
                              }
                           } catch (Throwable var10) {
                              if (var5 != null) {
                                 try {
                                    var5.close();
                                 } catch (Throwable var9x) {
                                    var10.addSuppressed(var9x);
                                 }
                              }

                              throw var10;
                           }

                           if (var5 != null) {
                              var5.close();
                           }
                        } catch (SQLException var11) {
                           this.showAlert(AlertType.ERROR, var11.getMessage());
                        }

                     });
                     var7.setScene(new Scene(var8, 400.0, 600.0));
                     var7.setTitle("Insert Values");
                     var7.show();
                     break label68;
                  }

                  this.showAlert(AlertType.ERROR, "Table not found or no columns.");
               } catch (Throwable var14) {
                  if (var4 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var13) {
                        var14.addSuppressed(var13);
                     }
                  }

                  throw var14;
               }

               if (var4 != null) {
                  var4.close();
               }

               return;
            }

            if (var4 != null) {
               var4.close();
            }
         } catch (SQLException var15) {
            this.showAlert(AlertType.ERROR, var15.getMessage());
         }

      });
      var1.setScene(new Scene(var4, 300.0, 200.0));
      var1.setTitle("Insert Values");
      var1.show();
   }

   private void showUpdateWindow() {
      Stage var1 = new Stage();
      TextField var2 = new TextField();
      TextField var3 = new TextField();
      TextField var4 = new TextField();
      Button var5 = new Button("Update");
      VBox var6 = new VBox(10.0, new Node[]{new Label("Table Name:"), var2, new Label("SET clause (e.g., name='Rahul', marks=95):"), var3, new Label("WHERE clause (e.g., id=10):"), var4, var5});
      var6.setStyle("-fx-padding: 20; -fx-alignment: center;");
      var5.setOnAction((var5x) -> {
         String var6 = var2.getText().trim();
         String var7 = var3.getText().trim();
         String var8 = var4.getText().trim();
         if (!var6.isEmpty() && !var7.isEmpty()) {
            String var9 = "UPDATE " + var6 + " SET " + var7;
            if (!var8.isEmpty()) {
               var9 = var9 + " WHERE " + var8;
            }

            try {
               Connection var10 = DriverManager.getConnection(this.url, this.user, this.password);

               try {
                  Statement var11 = var10.createStatement();
                  int var12 = var11.executeUpdate(var9);
                  this.showAlert(AlertType.INFORMATION, "" + var12 + " row(s) updated.");
                  var1.close();
               } catch (Throwable var14) {
                  if (var10 != null) {
                     try {
                        var10.close();
                     } catch (Throwable var13) {
                        var14.addSuppressed(var13);
                     }
                  }

                  throw var14;
               }

               if (var10 != null) {
                  var10.close();
               }
            } catch (SQLException var15) {
               this.showAlert(AlertType.ERROR, var15.getMessage());
            }

         } else {
            this.showAlert(AlertType.ERROR, "Table and SET clause must be provided.");
         }
      });
      var1.setScene(new Scene(var6, 500.0, 300.0));
      var1.setTitle("Update Records");
      var1.show();
   }

   private void showSimpleTableActionWindow(String var1, String var2) {
      Stage var3 = new Stage();
      TextField var4 = new TextField();
      VBox var5 = new VBox(10.0, new Node[]{new Label("Enter Table Name:"), var4});
      TextField var6 = new TextField();
      if (var1.equals("Delete")) {
         var5.getChildren().addAll(new Node[]{new Label("WHERE clause (optional):"), var6});
      }

      Button var7 = new Button(var1);
      var5.getChildren().add(var7);
      var5.setStyle("-fx-padding: 20; -fx-alignment: center;");
      var7.setOnAction((var6x) -> {
         String var7 = var4.getText().trim();
         if (var7.isEmpty()) {
            this.showAlert(AlertType.ERROR, "Table name is required.");
         } else {
            String var8 = var2 + var7;
            if (var1.equals("Delete")) {
               String var9 = var6.getText().trim();
               if (!var9.isEmpty()) {
                  var8 = var8 + " WHERE " + var9;
               }
            }

            try {
               Connection var15 = DriverManager.getConnection(this.url, this.user, this.password);

               try {
                  Statement var10 = var15.createStatement();
                  int var11 = var10.executeUpdate(var8);
                  this.showAlert(AlertType.INFORMATION, var1 + " successful! " + var11 + " row(s) affected.");
                  var3.close();
               } catch (Throwable var13) {
                  if (var15 != null) {
                     try {
                        var15.close();
                     } catch (Throwable var12) {
                        var13.addSuppressed(var12);
                     }
                  }

                  throw var13;
               }

               if (var15 != null) {
                  var15.close();
               }
            } catch (SQLException var14) {
               this.showAlert(AlertType.ERROR, var14.getMessage());
            }

         }
      });
      var3.setScene(new Scene(var5, 400.0, 250.0));
      var3.setTitle(var1 + " Table");
      var3.show();
   }

   private void getFieldDetails(String var1, int var2) {
      Stage var3 = new Stage();
      VBox var4 = new VBox(10.0);
      var4.setStyle("-fx-padding: 20; -fx-alignment: center;");
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();

      for(int var7 = 0; var7 < var2; ++var7) {
         TextField var8 = new TextField();
         var8.setPromptText("Field " + (var7 + 1) + " Name");
         ComboBox var9 = new ComboBox();
         var9.getItems().addAll(new String[]{"INT", "VARCHAR(100)", "DOUBLE", "DATE", "BOOLEAN"});
         var9.setValue("VARCHAR(100)");
         var4.getChildren().addAll(new Node[]{new Label("Field " + (var7 + 1)), var8, var9});
         var5.add(var8);
         var6.add(var9);
      }

      Button var10 = new Button("Create Table");
      var4.getChildren().add(var10);
      var10.setOnAction((var6x) -> {
         StringBuilder var7 = new StringBuilder("CREATE TABLE " + var1 + " (");

         for(int var8 = 0; var8 < var2; ++var8) {
            String var9 = ((TextField)var5.get(var8)).getText().trim();
            String var10 = (String)((ComboBox)var6.get(var8)).getValue();
            if (var9.isEmpty()) {
               this.showAlert(AlertType.ERROR, "Field name cannot be empty.");
               return;
            }

            var7.append(var9).append(" ").append(var10);
            if (var8 != var2 - 1) {
               var7.append(", ");
            }
         }

         var7.append(")");

         try {
            Connection var17 = DriverManager.getConnection(this.url, this.user, this.password);

            try {
               Statement var18 = var17.createStatement();

               try {
                  var18.executeUpdate(var7.toString());
                  this.showAlert(AlertType.INFORMATION, "Table created successfully!");
                  var3.close();
               } catch (Throwable var14) {
                  if (var18 != null) {
                     try {
                        var18.close();
                     } catch (Throwable var13) {
                        var14.addSuppressed(var13);
                     }
                  }

                  throw var14;
               }

               if (var18 != null) {
                  var18.close();
               }
            } catch (Throwable var15) {
               if (var17 != null) {
                  try {
                     var17.close();
                  } catch (Throwable var12) {
                     var15.addSuppressed(var12);
                  }
               }

               throw var15;
            }

            if (var17 != null) {
               var17.close();
            }
         } catch (SQLException var16) {
            this.showAlert(AlertType.ERROR, "Error creating table: " + var16.getMessage());
         }

      });
      Scene var11 = new Scene(var4, 400.0, 600.0);
      var3.setScene(var11);
      var3.setTitle("Define Table Fields");
      var3.show();
   }

   private void showAlert(Alert.AlertType var1, String var2) {
      Alert var3 = new Alert(var1, var2, new ButtonType[0]);
      var3.showAndWait();
   }
}
