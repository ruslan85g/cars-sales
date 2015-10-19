		<form action="/api/cars/save" method="post" enctype="application/x-www-form-urlencoded" >	
			<ul class="searchBar" >
				<li>
					<label>����</label>
				   	   <c:forEach var="Customer" items="${types}" >
                        <option value=""> <c:out value="${type}" />${type.car_type_name}</option>
                       </c:forEach>
					
					<select ng-model="updCar.car_type" ng-change="getNewTypeId(updCar.car_type);" >
						<option ng-selected="true" >��� ����</option>
						<option ng-repeat="(k,v) in man_opts track by $index"  >{{v.name}}</option>							
					</select>
				</li>
				<li>
					<label>���</label>
					<label>{{val.modName}}</label>
					<select ng-model="updCar.model" >
						<option ng-selected="true" >��� ���</option>
						<option ng-repeat="(k,v) in mod_opts track by $index" >{{v.model_name}}</option>							
					</select>
				</li>
				<li>
					<label>���</label>
					<label>${car.year}</label>
					<select ng-model="updCar.year" >
						<option ng-selected="true" >���</option>
						<option ng-repeat="year in years" >{{year}}</option>							
					</select>
				</li>
				<li>
					<label>���� �������</label>
					<label>${car.type_geare}</label>
					<select ng-model="updCar.type" >
						<option ng-selected="true" >���</option>
						<option>�����</option>
						<option>������</option>
					</select>
				</li>
				<li>
					<label>���:</label>
					<label>${car.volume}</label>
					<input type="number" placeholder="���" ng-model="updCar.volume" />
				</li>
				<li>
					<label>�"�:</label>
					<label>${car.km}</label>
					<input type="number" placeholder='�"�' ng-model="updCar.km" />
				</li>
				<li style=" margin-top: 10px;" >
					<label>����</label>
					<label>${car.price}</label>
					<input type="number" placeholder="����" ng-model="updCar.price" />
				</li>
				<li style=" margin-top: 10px;" >
					<label>���</label>
					<label>${car.color}</label>
					<input type="text" placeholder="���" ng-model="updCar.color" />
				</li>
				<li style="margin-top: 10px;" >
					<label>�����</label>
					<label></label>
					<input type="file" name="file" />
				</li>
				<li><button ng-click="editView = 0" class="button" >�����</button></li>
				<input type="submit" id="Upload" class="button" />
			</ul>
		</form>