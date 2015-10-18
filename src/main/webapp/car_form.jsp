
<form action="/api/cars/save" method="post"
	enctype="application/x-www-form-urlencoded">
	<ul class="searchBar">
		<li><label>׳™׳¦׳¨׳�</label> <select ng-model="newCar.car_type"
			ng-change="getListModel(newCar.car_type);">
				<option ng-selected="true">׳‘׳—׳¨ ׳™׳¦׳¨׳�</option>
				<option ng-repeat="(k,v) in man_opts track by $index">{{v.name}}</option>
		</select></li>
		<li><label>׳“׳’׳�</label> <select ng-model="newCar.model">
				<option ng-selected="true">׳‘׳—׳¨ ׳“׳’׳�</option>
				<option ng-repeat="(k,v) in mod_opts track by $index">{{v.model_name}}</option>
		</select></li>
		<li><label>׳©׳ ׳”</label> <select ng-model="newCar.year">
				<option ng-selected="true">׳©׳ ׳”</option>
				<option ng-repeat="year in years">{{year}}</option>
		</select></li>
		<li><label>׳×׳™׳‘׳× ׳”׳™׳�׳•׳›׳™׳�</label> <select
			ng-model="newCar.type">
				<option ng-selected="true">׳‘׳—׳¨</option>
				<option>׳™׳“׳ ׳™׳×</option>
				<option>׳�׳•׳˜׳•׳�׳˜</option>
		</select></li>
		<li><label>׳ ׳₪׳—:</label> <input type="number"
			placeholder="׳ ׳₪׳—" ng-model="newCar.volume" value="${car.volume}" />
		</li>
		<li><label>׳§"׳�:</label> <input type="number"
			placeholder='׳§"׳�' ng-model="newCar.km" value="${car.km}" /></li>
		<li style="margin-top: 10px;"><label>׳�׳—׳™׳¨</label> <input
			type="number" placeholder="׳�׳—׳™׳¨" ng-model="newCar.price"
			value="${car.price}" /></li>
		<li style="margin-top: 10px;"><label>׳¦׳‘׳¢</label> <input
			type="text" placeholder="׳¦׳‘׳¢" ng-model="newCar.color"
			value="${car.color}" /></li>
		<li style="margin-top: 10px;"><label>׳×׳�׳•׳ ׳”</label> <input
			type="file" name="file" /></li>
	</ul>
	<input type="submit" id="Upload" />
</form>