		<script>
			function onchangeFun(item){alert(item)}
		</script>
		<form action="/api/cars/save" method="post" enctype="application/x-www-form-urlencoded" >	
			<p>${types}</p>
			<ul class="searchBar" >
				<li>
					<label>יצרן</label>
					<select  onchange="onchangeFun(this);" >
				   	   <c:forEach var="Customer" items="${types}" >
                        <option value=""> <c:out value="${type}" /></option>
                       </c:forEach>
					</select>
				</li>
				<li>
					<label>דגם</label>
					<label>{{val.modName}}</label>
					<select ng-model="updCar.model" >
						<option ng-selected="true" >בחר דגם</option>
						<option ng-repeat="(k,v) in mod_opts track by $index" >{{v.model_name}}</option>							
					</select>
				</li>
				<li>
					<label>שנה</label>
					<label>${car.year}</label>
					<select ng-model="updCar.year" >
						<option ng-selected="true" >שנה</option>
						<option ng-repeat="year in years" >{{year}}</option>							
					</select>
				</li>
				<li>
					<label>תיבת הילוכים</label>
					<label>${car.type_geare}</label>
					<select ng-model="updCar.type" >
						<option ng-selected="true" >בחר</option>
						<option>ידנית</option>
						<option>אוטומט</option>
					</select>
				</li>
				<li>
					<label>נפח:</label>
					<label>${car.volume}</label>
					<input type="number" placeholder="נפח" ng-model="updCar.volume" />
				</li>
				<li>
					<label>ק"מ:</label>
					<label>${car.km}</label>
					<input type="number" placeholder='ק"מ' ng-model="updCar.km" />
				</li>
				<li style=" margin-top: 10px;" >
					<label>מחיר</label>
					<label>${car.price}</label>
					<input type="number" placeholder="מחיר" ng-model="updCar.price" />
				</li>
				<li style=" margin-top: 10px;" >
					<label>צבע</label>
					<label>${car.color}</label>
					<input type="text" placeholder="צבע" ng-model="updCar.color" />
				</li>
				<li style="margin-top: 10px;" >
					<label>תמונה</label>
					<label></label>
					<input type="file" name="file" />
				</li>
				<li><button ng-click="editView = 0" class="button" >ביתול</button></li>
				<input type="submit" id="Upload" class="button" />
			</ul>
		</form>