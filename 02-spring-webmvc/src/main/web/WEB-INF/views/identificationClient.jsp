<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<jsp:include page="_shared.jsp"></jsp:include>

<body>

	<form action="gestionDevis.do" method="post">

		<div class="panel panel-default" style="max-width: 800px; margin: auto;">
		
			<div class="panel-heading">
				<h2>Gestion des devis</h2>
				<h3>Identification du client</h3>
	 		</div>	 		

			<div> 
				<table class="table" style="width: auto;">
					<tr>
						<td><label for="nomClient">Nom client </label></td>
						<td><input type="text" name="nomClient" /></td>
					</tr>
					<tr>
						<td><label for="email">Email </label></td>
						<td><input type="text" name="email" /></td>
					</tr>
				</table>

				<div style="margin: 10px;">
					<button name="action" value="identifierClient" class="btn btn-default">
						Valider
					</button>
				</div>

			</div>

		</div>
	</form>

</body>
