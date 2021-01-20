<%@ page import="java.util.ArrayList"%>
<%@ page import="domain.Categorization"%>
<!DOCTYPE html>
<html>
<head>

    <!-- Basic -->
    <meta charset="UTF-8"/>

    <meta name="keywords" content="HTML5 Admin Template" />
    <meta name="description" content="Porto Admin - Responsive HTML5 Template">
    <meta name="author" content="okler.net">

    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

    <!-- Web Fonts  -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800|Shadows+Into+Light" rel="stylesheet" type="text/css">

    <!-- Vendor CSS -->
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="assets/vendor/font-awesome/css/font-awesome.css" />

    <!-- Theme CSS -->
    <link rel="stylesheet" href="assets/stylesheets/theme.css" />

    <!-- Skin CSS -->
    <link rel="stylesheet" href="assets/stylesheets/skins/default.css" />

    <!-- Theme Custom CSS -->
    <link rel="stylesheet" href="assets/stylesheets/theme-custom.css">

    <!-- Head Libs -->
    <script src="assets/vendor/modernizr/modernizr.js"></script>

</head>
<body>
<div class="container">
  <section class="panel panel-featured panel-featured-primary" style="margin-top: 20px;">
      <header class="panel-heading">
        <div class="panel-actions">
          <a href="#" class="panel-action panel-action-toggle" data-panel-toggle=""></a>
          <a href="#" class="panel-action panel-action-dismiss" data-panel-dismiss=""></a>
        </div>

        <h2 class="panel-title">Categorization</h2>
      </header>
           <div class="panel-body" style="display: block;">
            <form action="Connexion?action=Catego" method="post">
     <% if (request.getAttribute("categorization") != null ) {
    ArrayList<Categorization> categorization = (ArrayList<Categorization>) request.getAttribute("categorization");
    if (categorization.size() >0){
    	for (int i=0; i < categorization.size(); i++) {%>
        
              <div style="border-bottom: solid 1px #dedede; margin-bottom: 20px">
                <p><%out.println("Choisissez la méthode et la colonne que vous vouliez utiliser pour repésenter la clé étrangére "+categorization.get(i).getColumnName()+" de la table "+categorization.get(i).getTableName()+" venu de la table "+categorization.get(i).getForeignKeyRefTable());%></p>
                
                <div class="row">
                    <div class="col-md-4">
                      <div class="radio-custom radio-primary">
                          <input type="radio" value="subclass" name="<%out.print(i);%>_func" required>
                          <label for="subclass">Sub Class</label>
                      </div>
                    </div>
                    <div class="col-md-4">
                        <div class="radio-custom radio-primary">
                            <input type="radio" value="oneOf" name="<%out.print(i);%>_func" required>
                            <label for="oneOf">One Of</label>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <select name="<%out.print(i);%>_select" class="form-control mb-md">
                         <%
                        for(int j=0; j < categorization.get(i).getColumnsOfRefTable().size(); j++) {
                        %>
                          <option value="<%out.print(categorization.get(i).getColumnsOfRefTable().get(j));%>"><%out.print(categorization.get(i).getColumnsOfRefTable().get(j));%></option>
                        <%
                         }%>
                        </select>
                    </div>
                </div>
              
<%
    }}else {%>
            <div style="border-bottom: solid 1px #dedede; margin-bottom: 20px">
                <p>Nous n'avons pas detecter un cas de catégorisation Veiullez généré votre Ontologie</p>

    <%
    }}%>
             </div>

              <div>
                <input type="submit" value="Générer" class="btn btn-primary">
              </div>


        </form>
      </div>
    </section>
</div>


</body>
</html>


              

              