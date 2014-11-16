<div id="selectedCompany" class="row" data-bind="with : selectedCompany" style="display: none;">
	<div class="app-wrapper ui-corner-top">
		<div class="gray module ui-corner-top clearfix">
			<h2>
				详细信息<span class="subheader line" data-bind="text : name"></span>
			</h2>
			<h2 class="right">
				<a class="small blue button" data-bind="click : $root.trackCustomer" href="#">返回客户列表</a>
			</h2>
		</div>
		<div class="content">
			<div class="currentId" data-bind="text : id, visible : false"></div>
			<div class="row">
				<div class="six columns">
					<div class="row">
						<div class='two columns'>
							<label>区域 </label><input type="text" data-bind="value : distinct">
						</div>
						<div class='ten columns'>
							<label>地址 </label> <input type="text" data-bind="value : address">
						</div>
					</div>
					<div class="row">
						<label>电子邮件</label><label class="input-checkbox"> <img alt="电子邮箱" data-bind="attr: { 'src' : email_src }">
						</label>
					</div>

				</div>
				<div class="six columns">
					<label>公司简介</label>
					<textarea class="tall" name="ex-textarea-4" data-bind="text : name" data-bind="text : name"></textarea>
				</div>
			</div>
			<div class="row">
				<div class="three columns">
					<div class="app-wrapper ui-corner-top">
						<div class="module ui-corner-top clearfix">
							<h2>客户关注点</h2>
						</div>
						<div class="content">
							<ul>
								<li data-bind="foreach : problems">
									<div class="row">
										<div class="ten columns">
											<label class="btn btn-block btn-lg btn-success"> <span data-bind="text : $data"></span>
											</label>
										</div>
										<div class="two columns">
											<button class="tiny blue button">删除</button>
										</div>
									</div>
								</li>
								<li>
									<div class="row">
										<div class="ten columns">
											<select data-bind="options:$root.allProblemsConstant, optionsValue : 'id', optionsText : 'name', value : $parent.selectedProblem">
											</select>
										</div>
										<div class="two columns">
											<a href="#" class="tiny blue button" data-bind="click: $root.addProblem">增加</a>
										</div>
									</div>
								</li>
							</ul>
							<br>
						</div>
					</div>
				</div>
				<div class="three columns">
					<div class="app-wrapper ui-corner-top">
						<div class="module ui-corner-top clearfix">
							<h2>跟踪进度</h2>
						</div>
						<div class="content">
							<label class="input-checkbox selected" for="ex-chx-a"> <input type="checkbox" name="ex-checkbox" id="ex-chx-a" value="1" /> 第一次沟通
							</label> <label class=".selected input-checkbox" for="ex-chx-b"> <input type="checkbox" name="ex-checkbox" id="ex-chx-b" value="2" /> 第二次沟通
							</label>
						</div>
					</div>
				</div>
				<div class="six columns">
					<div class="app-wrapper ui-corner-top">
						<div class="module ui-corner-top clearfix">
							<h2>跟踪情况(心得体会)</h2>
							<h2 class="right">
								<a href="" class="nice radius blue tiny button">保存</a>
							</h2>
						</div>
						<div class="content">
							<textarea class="tall" name="ex-textarea-4">革命尚未成功，同志仍需努力！</textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>