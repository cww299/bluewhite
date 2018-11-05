<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
     <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>裁剪</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
  
   
</head>

<body>
    <section id="main-wrapper" class="theme-default">
        
        <%@include file="../../decorator/leftbar.jsp"%> 
        
        <!--main content start-->
        
           <section id="main-content" class="animated fadeInUp">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">裁剪详情</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
                             <div class="panel-body">
                                <div class="tab-wrapper tab-primary">
                                    <ul class="nav nav-tabs col-md-12">
                                        <li class="active col-md-2" style="width: 14.285%"><a href="#home1" class="home1" data-toggle="tab">裁剪页面</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%;"><a href="#profile1" class="profile1"  data-toggle="tab">裁剪普通激光</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%"><a href="#profile2" class="profile2" data-toggle="tab">绣花定位激光</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%"><a href="#profile3" class="profile3" data-toggle="tab">冲床</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%"><a href="#profile4" class="profile4" data-toggle="tab">电烫</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%"><a href="#profile5" class="profile5" data-toggle="tab">电推</a>
                                        </li>
                                        <li class="col-md-2"style="width: 14.285%"><a href="#profile6" class="profile6" data-toggle="tab">手工剪刀</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="home1">
            <!-- 查询结束 -->
                                        <div class="row" style="height: 30px; margin:15px 0 10px">
			<div class="col-xs-11 col-sm-11  col-md-11">
				<form class="form-search" >
					<div class="row">
						<div class="col-xs-11 col-sm-11 col-md-11">
							<div class="input-group"> 
								<table><tr>
								<td>产品名:</td><td><input type="text" name="name" id="productName" placeholder="请输入产品名称" class="form-control search-query name" data-provide="typeahead" autocomplete="off"/ ></td>
								<td>&nbsp&nbsp</td>
								<td>默认数量:</td><td><input type="text" name="number" id="number" disabled="disabled" placeholder="请输入默认数量" class="form-control search-query number" /></td>
									<td>&nbsp&nbsp</td>
								<td>默认耗损:</td><td><input type="text" name="name" id="loss" placeholder="请输入产品名称" class="form-control search-query name" /></td>
								<td>&nbsp&nbsp</td>
								<td>裁剪价格:</td><td><input type="text" name="name" id="ntwo" disabled="disabled"  class="form-control search-query name" /></td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtaskks">
											查&nbsp找
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
                                            <table class="table table-hover" >
                                    <thead>
                                        <tr>
                                        	<th class="text-center">裁剪部位</th>
                                        	<th class="text-center">裁剪片数</th>
                                            <th class="text-center">当批片数</th>
                                            <th class="text-center">裁片的平方M</th>
                                            <th class="text-center">裁剪方式</th>
                                            <th class="text-center">得到理论(市场反馈）含管理价值</th>
                                            <th class="text-center">得到实验推算价格</th>
                                            <th class="text-center">市场价与实推价比</th>
                                            <th class="text-center">选择入成本价格↓</th>
                                            <th class="text-center">入成本价格</th>
                                            <th class="text-center">各单道比全套工价</th>
                                            <th class="text-center">物料压价</th>
                                            <th class="text-center">不含绣花环节的为机工压价</th>
                                            <th class="text-center">含绣花环节的为机工压价</th>
                                            <th class="text-center">为机工准备的压价</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent">
                                        
                                    </tbody>
                                </table>
                                        </div>
                     <!-- B工资流水开始 -->
            <div class="tab-pane" id="profile1">
                      <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
							<table><tr>
								<td>裁剪价格:</td><td><input type="text" name="name" id="ntwo1" disabled="disabled"  class="form-control search-query name" /></td>
								</tr></table>
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d navbar-right searchtask">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->  
                                   <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">裁剪部位</th>
                                        	<th class="text-center">手选裁剪方式</th>
                                            <th class="text-center">裁片周长/CM(≈)</th>
                                            <th class="text-center">激光停顿点</th>
                                            <th class="text-center">单双激光头</th>
                                            <th class="text-center">捡片时间</th>
                                            <th class="text-center">其他未考虑时间1</th>
                                            <th class="text-center">其他未考虑时间2</th>
                                            <th class="text-center">拉布时间</th>
                                            <th class="text-center">单片激光需要用净时</th>
                                            <th class="text-center">单片激光放快手时间</th>
                                            <th class="text-center">工价（含快手)</th>
                                            <th class="text-center">设备折旧和房水电费</th>
                                            <th class="text-center">管理人员费用</th>
                                            <th class="text-center">普通激光切割该裁片费用</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent2">
                                        
                                    </tbody>
                                </table>
                                 </div>
                                 <!-- B工资流水结束 -->
                 <div class="tab-pane" id="profile2">
                                     <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr>
								<td>裁剪价格:</td><td><input type="text" name="name" id="ntwo2" disabled="disabled"  class="form-control search-query name" /></td>
								</tr></table>
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d navbar-right searchtask2">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
                                    
                                            <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">裁剪部位</th>
                                        	<th class="text-center">手选裁剪方式</th>
                                            <th class="text-center">裁片周长/CM(≈)</th>
                                            <th class="text-center">激光停顿点</th>
                                            <th class="text-center">单双激光头</th>
                                            <th class="text-center">捡片时间</th>
                                            <th class="text-center">绣切的撕片时间</th>
                                            <th class="text-center">其他考虑时间</th>
                                            <th class="text-center">拉布时间</th>
                                            <th class="text-center">单片激光需要用净时</th>
                                            <th class="text-center">单片激光放快手时间</th>
                                            <th class="text-center">工价（含快手)</th>
                                            <th class="text-center">设备折旧和房水电费</th>
                                            <th class="text-center">管理人员费用</th>
                                            <th class="text-center">普通激光切割该裁片费用</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent3">
                                        
                                    </tbody>
                                </table>
                               </div>
                               
                               
                               <div class="tab-pane" id="profile3">
                                     <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr>
								<td>裁剪价格:</td><td><input type="text" name="name" id="ntwo3" disabled="disabled"  class="form-control search-query name" /></td>
								</tr></table>
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d navbar-right searchtask3">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
                                    
                                            <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">裁剪部位</th>
                                        	<th class="text-center">手选裁剪方式</th>
                                            <th class="text-center">选择叠片层数</th>
                                            <th class="text-center">其他未考虑时间1</th>
                                            <th class="text-center">其他未考虑时间2</th>
                                            <th class="text-center">其他未考虑时间3</th>
                                            <th class="text-center">叠布秒数（含快手)</th>
                                            <th class="text-center">冲压秒数（含快手)</th>
                                            <th class="text-center">工价（含快手)</th>
                                            <th class="text-center">设备折旧和房水电费</th>
                                            <th class="text-center">管理人员费用</th>
                                            <th class="text-center">普通激光切割该裁片费用</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent4">
                                        
                                    </tbody>
                                </table>
                                        </div>
                               
                               
                               
                               <div class="tab-pane" id="profile4">
                                     <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr>
								<td>裁剪价格:</td><td><input type="text" name="name" id="ntwo4" disabled="disabled"  class="form-control search-query name" /></td>
								</tr></table>
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d navbar-right searchtask4">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
                                    
                                            <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">裁剪部位</th>
                                        	<th class="text-center">手选裁剪方式</th>
                                            <th class="text-center">选择一板排版片数</th>
                                            <th class="text-center">其他未考虑时间1</th>
                                            <th class="text-center">其他未考虑时间2</th>
                                            <th class="text-center">电烫秒数（含快手)</th>
                                            <th class="text-center">撕片秒数（含快手)</th>
                                            <th class="text-center">拉布秒数（含快手)</th>
                                            <th class="text-center">电烫工价（含快手)</th>
                                            <th class="text-center">撕片工价</th>
                                            <th class="text-center">设备折旧和房水电费</th>
                                            <th class="text-center">管理人员费用</th>
                                            <th class="text-center">普通激光切割该裁片费用</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent5">
                                        
                                    </tbody>
                                </table>
                                        </div>
                               
                               
                             
                               
                               
                               
                               <div class="tab-pane" id="profile5">
                                     <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
							<table><tr>
								<td>裁剪价格:</td><td><input type="text" name="name" id="ntwo5" disabled="disabled"  class="form-control search-query name" /></td>
								</tr></table>
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm navbar-right btn-3d searchtask5">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
                                    
                                            <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">裁剪部位</th>
                                        	<th class="text-center">手选裁剪方式</th>
                                            <th class="text-center">选择叠片层数</th>
                                            <th class="text-center">裁片周长/CM(≈)</th>
                                            <th class="text-center">其他未考虑时间2</th>
                                            <th class="text-center">其他未考虑时间3</th>
                                            <th class="text-center">叠布秒数（含快手)</th>
                                            <th class="text-center">电推秒数（含快手)</th>
                                            <th class="text-center">工价（含快手)</th>
                                            <th class="text-center">设备折旧和房水电费</th>
                                            <th class="text-center">管理人员费用</th>
                                            <th class="text-center">普通激光切割该裁片费用</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent6">
                                        
                                    </tbody>
                                </table>
                                        </div>
                               
                               
                               
                               <div class="tab-pane" id="profile6">
                                     <!--查询开始  -->
          		 <div class="row" style="height: 30px; margin:15px 0 10px">
					<div class="col-xs-8 col-sm-8  col-md-8">
						<form class="form-search" >
							<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group">
							<table><tr>
								<td>裁剪价格:</td><td><input type="text" name="name" id="ntwo6" disabled="disabled"  class="form-control search-query name" /></td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm navbar-right btn-3d searchtask6">
										查找
										<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
            <!-- 查询结束 -->
                                    
                                            <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">裁剪部位</th>
                                        	<th class="text-center">手选裁剪方式</th>
                                            <th class="text-center">裁片周长/CM(≈)</th>
                                            <th class="text-center">其他未考虑时间2</th>
                                            <th class="text-center">其他未考虑时间3</th>
                                            <th class="text-center">手工秒数（含快手)</th>
                                            <th class="text-center">工价（含快手)</th>
                                            <th class="text-center">设备折旧和房水电费</th>
                                            <th class="text-center">管理人员费用</th>
                                            <th class="text-center">普通激光切割该裁片费用</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent7">
                                        
                                    </tbody>
                                </table>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>
                        </div>
            </section>
        </section>


<div class="wrap">
<div class="layer-right" style="display: none;">
           <div class=" col-xs-12  col-sm-12  col-md-12">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12" >
									<table>
                                        <tr>
                                       		<th class="text-center">当下周边地区激光每米/元:</th><td><input type="text" id="peripheralLaser"  class="form-control"></td>
                                       		<td><input type="text" id="ordid" class="hidden"></td>
                                       		<th><button type="button" class="btn btn-info  btn-sm  btn-trans updateord">修改</button></th>
                                       </tr>
                                       <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">激光机秒走CM?:</th><td><input type="text" id="extent"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每CM 用时/秒:</th><td><input type="text" disabled="disabled" id="time"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">激光片每个停顿点用秒？:</th><td><input type="text"  id="pauseTime" class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">1.5M*1M拉布平铺时间:</th><td><input type="text" id="rabbTime"  class="form-control actualtimetw"></td>
                                            <th class="text-center">被/数:</th><td><input type="text" id="quilt"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">激光机放快手比:</th><td><input type="text"  id="quickWorker" class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">激光机设备价值:</th><td><input type="text"  id="worth" class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒设备折旧费用:</th><td><input type="text" disabled="disabled" id="depreciation"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">设置分摊天数:</th><td><input type="text" id="shareDay"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每天机器工作时间设置/小时:</th><td><input type="text" id="workTime"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">激光管费用:</th><td><input type="text" id="laserTubePrice"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒激光管费用:</th><td><input type="text" disabled="disabled" id="laserTubePriceSecond"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">分摊小时:</th><td><input type="text" id="shareTime"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒维护费用:</th><td><input type="text" disabled="disabled" id="maintenanceChargeSecond"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">维护费用:</th><td><input type="text" id="maintenanceCharge"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">分摊小时:</th><td><input type="text" id="shareTimeTwo"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗电/元:</th><td><input type="text" id="omnHorElectric"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗水/元:</th><td><input type="text" id="omnHorWater"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒耗3费:</th><td><input type="text" disabled="disabled" id="perSecondPrice"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗房租/元:</th><td><input type="text" id="omnHorHouse"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时站机工价:</th><td><input type="text" id="omnHorMachinist"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒工价:</th><td><input type="text" disabled="disabled" id="perSecondMachinist"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">制版分配任务管理人员工资:</th><td><input type="text" id="managePrice"   class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒管理费用:</th><td><input type="text" disabled="disabled" id="perSecondManage"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">管理设备数量:</th><td><input type="text" id="manageEquipmentNumber"  class="form-control actualtimetw"></td>
                                            <th class="text-center">设置激光设备利润比:</th><td><input type="text" id="equipmentProfit"  class="form-control actualtimetw"></td>
                                        </tr>
                                    </table>
                    	</div>
                 		
				</form>
                 </div>
				</div>
  </div>
</div>

<div class="wrap">
<div class="layer-right2" style="display: none;">
           <div class=" col-xs-12  col-sm-12  col-md-12">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12" >
									<table>
										<tr>
                                        	<th class="text-center">周长小于左侧/片/元:</th><td><input type="text" id="perimeterLess2"  class="form-control actualtimetw"></td>
                                            <td>&nbsp&nbsp&nbsp&nbsp</td><td><input type="text"  id="perimeterLessNumber2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                        <tr>
                                       		<th class="text-center">当下周边地区激光每米/元:</th><td><input type="text" id="peripheralLaser2"  class="form-control"></td>
                                       		<td><input type="text" id="ordid2" class="hidden"></td>
                                       		<th><button type="button" class="btn btn-info  btn-sm  btn-trans updateord">修改</button></th>
                                       </tr>
                                       <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">激光机秒走CM?:</th><td><input type="text" id="extent2"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每CM 用时/秒:</th><td><input type="text" disabled="disabled" id="time2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">激光片每个停顿点用秒？:</th><td><input type="text"  id="pauseTime2" class="form-control actualtimetw"></td>
                                        	<th class="text-center">绣花激光撕片/片:</th><td><input type="text"  id="embroideryLaserNumber2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">1.5M*1M拉布平铺时间:</th><td><input type="text" id="rabbTime2"  class="form-control actualtimetw"></td>
                                            <th class="text-center">被/数:</th><td><input type="text" id="quilt2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">激光机放快手比:</th><td><input type="text"  id="quickWorker2" class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">激光机设备价值:</th><td><input type="text"  id="worth2" class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒设备折旧费用:</th><td><input type="text" disabled="disabled" id="depreciation2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">设置分摊天数:</th><td><input type="text" id="shareDay2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每天机器工作时间设置/小时:</th><td><input type="text" id="workTime2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">激光管费用:</th><td><input type="text" id="laserTubePrice2"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒激光管费用:</th><td><input type="text" disabled="disabled" id="laserTubePriceSecond2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">分摊小时:</th><td><input type="text" id="shareTime2"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒维护费用:</th><td><input type="text" disabled="disabled" id="maintenanceChargeSecond2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">维护费用:</th><td><input type="text" id="maintenanceCharge2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">分摊小时:</th><td><input type="text" id="shareTimeTwo2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗电/元:</th><td><input type="text" id="omnHorElectric2"  class="form-control actualtimetw"></td>
                                     	 	<th class="text-center">每小时耗房租/元:</th><td><input type="text" id="omnHorHouse2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗水/元:</th><td><input type="text" id="omnHorWater2"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒耗3费:</th><td><input type="text" disabled="disabled" id="perSecondPrice2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时站机工价:</th><td><input type="text" id="omnHorMachinist2"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒工价:</th><td><input type="text" disabled="disabled" id="perSecondMachinist2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">制版分配任务管理人员工资:</th><td><input type="text" id="managePrice2"   class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒管理费用:</th><td><input type="text" disabled="disabled" id="perSecondManage2"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">管理设备数量:</th><td><input type="text" id="manageEquipmentNumber2"  class="form-control actualtimetw"></td>
                                            <th class="text-center">设置激光设备利润比:</th><td><input type="text" id="equipmentProfit2"  class="form-control actualtimetw"></td>
                                        </tr>
                                    </table>
                    	</div>
                 		
				</form>
                 </div>
				</div>
  </div>
  
  
  <div class="wrap">
<div class="layer-right3" style="display: none;">
           <div class=" col-xs-12  col-sm-12  col-md-12">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12" >
									<table>
                                        
                                       <tr>
                                        	<th class="text-center">每层拉布时间:</th><td><input type="text" id="puncherOne3"  class="form-control actualtimetw"></td>
                                            <th class="text-center">默认最少冲量:</th><td><input type="text"  id="puncherTwo3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每层拉布宽度/米:</th><td><input type="text" id="puncherThree3"  class="form-control actualtimetw"></td>
                                            <th class="text-center">默认批量少于冲量的叠布和冲压秒数:</th><td><input type="text" id="puncherFour3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                       		<th class="text-center">冲压秒数:</th><td><input type="text" id="puncherFive3"  class="form-control"></td>
                                       		<td><input type="text" id="ordid3" class="hidden"></td>
                                       		<th><button type="button" class="btn btn-info  btn-sm  btn-trans updateord">修改</button></th>
                                       </tr>
                                       <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">冲床机放快手比:</th><td><input type="text"  id="quickWorker3" class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">冲床设备价值:</th><td><input type="text"  id="worth3" class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒设备折旧费用:</th><td><input type="text" disabled="disabled" id="depreciation3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">设置分摊天数:</th><td><input type="text" id="shareDay3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每天机器工作时间设置/小时:</th><td><input type="text" id="workTime3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">刀模费用:</th><td><input type="text" id="laserTubePrice3"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒刀模费用:</th><td><input type="text" disabled="disabled" id="laserTubePriceSecond3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">分摊小时:</th><td><input type="text" id="shareTime3"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒维护费用:</th><td><input type="text" disabled="disabled" id="maintenanceChargeSecond3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">维护费用:</th><td><input type="text" id="maintenanceCharge3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">分摊小时:</th><td><input type="text" id="shareTimeTwo3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗电/元:</th><td><input type="text" id="omnHorElectric3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗水/元:</th><td><input type="text" id="omnHorWater3"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒耗3费:</th><td><input type="text" disabled="disabled" id="perSecondPrice3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗房租/元:</th><td><input type="text" id="omnHorHouse3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时站机工价:</th><td><input type="text" id="omnHorMachinist3"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒工价:</th><td><input type="text" disabled="disabled" id="perSecondMachinist3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">制版分配任务管理人员工资:</th><td><input type="text" id="managePrice3"   class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒管理费用:</th><td><input type="text" disabled="disabled" id="perSecondManage3"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">管理设备数量:</th><td><input type="text" id="manageEquipmentNumber3"  class="form-control actualtimetw"></td>
                                            <th class="text-center">设置激光设备利润比:</th><td><input type="text" id="equipmentProfit3"  class="form-control actualtimetw"></td>
                                        </tr>
                                    </table>
                    	</div>
                 		
				</form>
                 </div>
				</div>
  </div>
  
  
  <div class="wrap">
<div class="layer-right4" style="display: none;">
           <div class=" col-xs-12  col-sm-12  col-md-12">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12" >
									<table>
                                        
                                       <tr>
                                        	<th class="text-center">每层拉布时间:</th><td><input type="text" id="permOne4"  class="form-control actualtimetw"></td>
                                            <td>&nbsp&nbsp&nbsp&nbsp</td><td><select id="permFour4"  class="form-control actualtimetw"><option value="0">易</option><option value="1">难</option></select></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每层拉布宽度/米:</th><td><input type="text" id="permTwo4"  class="form-control actualtimetw"></td>
                                            <td>&nbsp&nbsp&nbsp&nbsp</td><td><input type="text" id="permFive4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                       		<th class="text-center">电烫秒数:</th><td><input type="text" id="permThree4"  class="form-control"></td>
                                       		<td><input type="text" id="ordid4" class="hidden"></td>
                                       		<th><button type="button" class="btn btn-info  btn-sm  btn-trans updateord">修改</button></th>
                                       </tr>
                                       <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">电烫放快手比:</th><td><input type="text"  id="quickWorker4" class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">电烫设备价值:</th><td><input type="text"  id="worth4" class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒设备折旧费用:</th><td><input type="text" disabled="disabled" id="depreciation4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">设置分摊天数:</th><td><input type="text" id="shareDay4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每天机器工作时间设置/小时:</th><td><input type="text" id="workTime4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">刀模费用:</th><td><input type="text" id="laserTubePrice4"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒刀模费用:</th><td><input type="text" disabled="disabled" id="laserTubePriceSecond4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">分摊小时:</th><td><input type="text" id="shareTime4"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒维护费用:</th><td><input type="text" disabled="disabled" id="maintenanceChargeSecond4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">维护费用:</th><td><input type="text" id="maintenanceCharge4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">分摊小时:</th><td><input type="text" id="shareTimeTwo4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗电/元:</th><td><input type="text" id="omnHorElectric4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗水/元:</th><td><input type="text" id="omnHorWater4"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒耗3费:</th><td><input type="text" disabled="disabled" id="perSecondPrice4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗房租/元:</th><td><input type="text" id="omnHorHouse4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时站机工价:</th><td><input type="text" id="omnHorMachinist4"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒工价:</th><td><input type="text" disabled="disabled" id="perSecondMachinist4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                      <tr>
                                        	<th class="text-center">每小时辅助工价:</th><td><input type="text" id="omnHorAuxiliary4"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒工价:</th><td><input type="text" disabled="disabled" id="perSecondMachinistTwo4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">制版分配任务管理人员工资:</th><td><input type="text" id="managePrice4"   class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒管理费用:</th><td><input type="text" disabled="disabled" id="perSecondManage4"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">管理设备数量:</th><td><input type="text" id="manageEquipmentNumber4"  class="form-control actualtimetw"></td>
                                            <th class="text-center">设置激光设备利润比:</th><td><input type="text" id="equipmentProfit4"  class="form-control actualtimetw"></td>
                                        </tr>
                                    </table>
                    	</div>
                 		
				</form>
                 </div>
				</div>
  </div>
  
  
 
 <div class="wrap">
<div class="layer-right5" style="display: none;">
           <div class=" col-xs-12  col-sm-12  col-md-12">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12" >
									<table>
                                        
                                       <tr>
                                        	<th class="text-center">每层拉布时间/秒:</th><td><input type="text" id="electricPushOne5"  class="form-control actualtimetw"></td>
                                        	<th class="text-center">每层拉布宽度/米:</th><td><input type="text" id="electricPushTwo5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                       		<th class="text-center">固定边缘秒数:</th><td><input type="text" id="electricPushThree5"  class="form-control"></td>
                                       		<td><input type="text" id="ordid5" class="hidden"></td>
                                       		<th><button type="button" class="btn btn-info  btn-sm  btn-trans updateord">修改</button></th>
                                       </tr>
                                       <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">画版时间/片/秒:</th><td><input type="text" id="electricPushFour5"  class="form-control actualtimetw"></td>
                                        </tr>
                                      	<tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">电推机秒走CM?:</th><td><input type="text"  id="electricPushFive5" class="form-control actualtimetw"></td>
                                            <th class="text-center">每CM 用时/秒:</th><td><input type="text" disabled="disabled" id="time5"  class="form-control actualtimetw"></td>
                                        </tr>
                                       <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">电推放快手比:</th><td><input type="text"  id="quickWorker5" class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">电推设备价值:</th><td><input type="text"  id="worth5" class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒设备折旧费用:</th><td><input type="text" disabled="disabled" id="depreciation5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">设置分摊天数:</th><td><input type="text" id="shareDay5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每天机器工作时间设置/小时:</th><td><input type="text" id="workTime5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">刀模费用:</th><td><input type="text" id="laserTubePrice5"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒刀模费用:</th><td><input type="text" disabled="disabled" id="laserTubePriceSecond5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">分摊小时:</th><td><input type="text" id="shareTime5"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒维护费用:</th><td><input type="text" disabled="disabled" id="maintenanceChargeSecond5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">维护费用:</th><td><input type="text" id="maintenanceCharge5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">分摊小时:</th><td><input type="text" id="shareTimeTwo5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗电/元:</th><td><input type="text" id="omnHorElectric5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗水/元:</th><td><input type="text" id="omnHorWater5"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒耗3费:</th><td><input type="text" disabled="disabled" id="perSecondPrice5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗房租/元:</th><td><input type="text" id="omnHorHouse5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时站机工价:</th><td><input type="text" id="omnHorMachinist5"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒工价:</th><td><input type="text" disabled="disabled" id="perSecondMachinist5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">制版分配任务管理人员工资:</th><td><input type="text" id="managePrice5"   class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒管理费用:</th><td><input type="text" disabled="disabled" id="perSecondManage5"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">管理设备数量:</th><td><input type="text" id="manageEquipmentNumber5"  class="form-control actualtimetw"></td>
                                            <th class="text-center">设置激光设备利润比:</th><td><input type="text" id="equipmentProfit5"  class="form-control actualtimetw"></td>
                                        </tr>
                                    </table>
                    	</div>
                 		
				</form>
                 </div>
				</div>
  </div> 
  
  
  <div class="wrap">
<div class="layer-right6" style="display: none;">
           <div class=" col-xs-12  col-sm-12  col-md-12">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12" >
									<table>
                                        
                                       <tr>
                                       		<td><input type="text" id="ordid6" class="hidden"></td>
                                       		<th><button type="button" class="btn btn-info  btn-sm  btn-trans updateord">修改</button></th>
                                       </tr>
                                      	<tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">手剪每秒走CM?:</th><td><input type="text"  id="manualOne6" class="form-control actualtimetw"></td>
                                            <th class="text-center">每CM 用时/秒:</th><td><input type="text" disabled="disabled" id="time6"  class="form-control actualtimetw"></td>
                                        </tr>
                                       <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">手剪放快手比:</th><td><input type="text"  id="quickWorker6" class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗电/元:</th><td><input type="text" id="omnHorElectric6"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗水/元:</th><td><input type="text" id="omnHorWater6"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒耗3费:</th><td><input type="text" disabled="disabled" id="perSecondPrice6"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时耗房租/元:</th><td><input type="text" id="omnHorHouse6"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">每小时站机工价:</th><td><input type="text" id="omnHorMachinist6"  class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒工价:</th><td><input type="text" disabled="disabled" id="perSecondMachinist6"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">制版分配任务管理人员工资:</th><td><input type="text" id="managePrice6"   class="form-control actualtimetw"></td>
                                            <th class="text-center">每秒管理费用:</th><td><input type="text" disabled="disabled" id="perSecondManage6"  class="form-control actualtimetw"></td>
                                        </tr>
                                        <tr><td><div style="height: 10px"></div></td></tr>
                                       <tr>
                                        	<th class="text-center">管理设备数量:</th><td><input type="text" id="manageEquipmentNumber6"  class="form-control actualtimetw"></td>
                                            <th class="text-center">设置激光设备利润比:</th><td><input type="text" id="equipmentProfit6"  class="form-control actualtimetw"></td>
                                        </tr>
                                    </table>
                    	</div>
                 		
				</form>
                 </div>
				</div>
  </div> 
    </section>
    
   
   
   <script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
    <script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx }/static/plugins/navgoco/jquery.navgoco.min.js"></script>
    <script src="${ctx }/static/plugins/switchery/switchery.min.js"></script>
    <script src="${ctx }/static/plugins/pace/pace.min.js"></script>
    <script src="${ctx }/static/plugins/fullscreen/jquery.fullscreen-min.js"></script>
    <script src="${ctx }/static/js/src/app.js"></script>
     <script src="${ctx }/static/js/laypage/laypage.js"></script> 
    <script src="${ctx }/static/plugins/dataTables/js/jquery.dataTables.js"></script>
    <script src="${ctx }/static/plugins/dataTables/js/dataTables.bootstrap.js"></script>
    <script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
    <script src="${ctx }/static/js/vendor/mSlider.min.js"></script>
    <script src="${ctx }/static/js/vendor/typeahead.js"></script>
    <script>
   jQuery(function($){
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
			var _cache;
			this.setCache = function(cache){
		  		_cache=cache;
		  	}
		  	this.getCache = function(){
		  		return _cache;
		  	}
		  	this.getCount = function(){
		  		return _count;
		  	}
		  	this.setCount = function(count){
		  		_count=count;
		  	}
		  	var productIdAll="${productId}";
		  	var productNameAll="${productNamexx}";
		  	var productNumberAll="${productNumberxx}";
		  	self.setCache(productIdAll)
		  	$("#productName").val(productNameAll);
		  	$("#number").val(productNumberAll);
				var data={
						page:1,
				  		size:100,	
				  		productId:productIdAll,
				}
				var data2={
						page:1,
				  		size:100,	
				  		productId:productIdAll,
				  		tailorTypeId:71,
				}
				var data3={
						page:1,
				  		size:100,	
				  		productId:productIdAll,
				  		tailorTypeId:72,
				}
				var data4={
						page:1,
				  		size:100,	
				  		productId:productIdAll,
				  		tailorTypeId:75,
				}
				var data5={
						page:1,
				  		size:100,	
				  		productId:productIdAll,
				  		tailorTypeId:73,
				}
				var data6={
						page:1,
				  		size:100,	
				  		productId:productIdAll,
				  		tailorTypeId:76,
				}
				var data7={
						page:1,
				  		size:100,	
				  		productId:productIdAll,
				  		tailorTypeId:77,
				}
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
				self.loadPagination2(data2);
				self.loadPagination3(data3);
				self.loadPagination4(data4);
				self.loadPagination5(data5);
				self.loadPagination6(data6);
				self.loadPagination7(data7);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/product/getTailor",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			$("#ntwo").val(result.data.rows[0].oneCutPrice)
		      			 $(result.data.rows).each(function(i,o){
		      				 if(o.costPrice==o.managePrice || o.costPrice==o.experimentPrice){
		      					 
		      				 }else{
		      					o.costPrice=""
		      				 }
		      				 html +='<tr><td class="center reste hidden"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center edit ">'+o.tailorName+'</td>'
		      				+'<td class="text-center edit ">'+o.tailorNumber+'</td>'
		      				+'<td class="text-center edit ">'+o.bacthTailorNumber+'</td>'
		      				+'<td class="text-center edit "><input class="form-control tailorSize"  value='+(o.tailorSize!=null?o.tailorSize:"")+'></td>'
		      				+'<td class="text-center edit tailorType" data-tailortypeid='+o.tailorTypeId+'></td>'
		      				+'<td class="text-center managePrice">'+(o.managePrice!=null?o.managePrice:"")+'</td>'
		      				+'<td class="text-center experimentPrice">'+parseFloat((o.experimentPrice*1).toFixed(5))+'</td>'
		      				+'<td class="text-center edit ratePrice">'+parseFloat((o.ratePrice*1).toFixed(4))+'</td>'
		      				+'<td class="text-center edit selectcostprice"><select class="form-control costPrice"><option value="'+(o.costPrice!=null?o.costPrice:"")+'">'+(o.costPrice!=null?o.costPrice:"")+'</option><option value="'+(o.managePrice!=null?o.managePrice:"")+'">'+(o.managePrice!=null?o.managePrice:"")+'</option><option value="'+(o.experimentPrice!=null?o.experimentPrice:"")+'">'+(o.experimentPrice!=null?o.experimentPrice:"")+'</option></select></td>'
		      				+'<td class="text-center edit allCostPrice">'+parseFloat((o.allCostPrice*1).toFixed(3))+'</td>'
		      				+'<td class="text-center edit scaleMaterial">'+parseFloat((o.scaleMaterial*1).toFixed(5))+'</td>'
		      				+'<td class="text-center edit ">'+parseFloat((o.priceDown*1).toFixed(4))+'</td>'
		      				+'<td class="text-center edit noeMbroiderPriceDown">'+parseFloat((o.noeMbroiderPriceDown*1).toFixed(4))+'</td>'
		      				+'<td class="text-center edit ">'+parseFloat((o.embroiderPriceDown*1).toFixed(4))+'</td>'
		      				+'<td class="text-center edit ">'+parseFloat((o.machinistPriceDown*1).toFixed(4))+'</td></tr>'
		      			}); 
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   self.loadEvents()
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			  this.loadPagination2 = function(data2){
				  //裁剪普通激光
				  $(".profile1").on('click',function(){
					  var index;
					    var html = '';
					    $.ajax({
						      url:"${ctx}/product/getOrdinaryLaser",
						      data:data2,
						      type:"GET",
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			$("#ntwo1").val(result.data.rows[0].oneCutPrice)
				      			 $(result.data.rows).each(function(i,o){
				      				 html +='<tr>'
				      				+'<td class="text-center edit ">'+o.tailorName+'</td>'
				      				+'<td class="text-center tailorType2" data-tailortypeid2='+o.tailorTypeId+'></td>'
				      				+'<td class="text-center"><input class="form-control perimeter2" data-id="'+o.id+'" style="width: 80px;" value='+(o.perimeter!=0?o.perimeter:"")+'></td>'
				      				+'<td class="text-center edit"><input class="form-control stallPoint2" data-id="'+o.id+'" style="width: 50px;" value='+(o.stallPoint!=0?o.stallPoint:"")+'></td>'
				      				+'<td class="text-center edit"><select class="form-control singleDouble2" data-id="'+o.id+'" data-singledouble="'+o.singleDouble+'" style="width: 100px;"><option value="3">请选择</option><option value="1">单</option><option value="2">双</option></select></td>'
				      				+'<td class="text-center"><input class="form-control time2" data-id="'+o.id+'" style="width: 50px;"  value='+(o.time!=null?o.time:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeOne2" data-id="'+o.id+'" style="width: 80px;" value='+(o.otherTimeOne!=null?o.otherTimeOne:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeTwo2" data-id="'+o.id+'" style="width: 80px;" value='+(o.otherTimeTwo!=null?o.otherTimeTwo:"")+'></td>'
				      				+'<td class="text-center edit rabbTime2">'+parseFloat((o.rabbTime).toFixed(5))+'</td>'
				      				+'<td class="text-center edit singleLaserTime2">'+parseFloat((o.singleLaserTime).toFixed(5))+'</td>'
				      				+'<td class="text-center edit singleLaserHandTime2">'+parseFloat((o.singleLaserHandTime).toFixed(5))+'</td>'
				      				+'<td class="text-center edit labourCost2">'+parseFloat((o.labourCost).toFixed(8))+'</td>'
				      				+'<td class="text-center edit equipmentPrice2">'+parseFloat((o.equipmentPrice).toFixed(5))+'</td>'
				      				+'<td class="text-center edit administrativeAtaff2">'+parseFloat((o.administrativeAtaff).toFixed(5))+'</td>'
				      				+'<td class="text-center edit stallPrice2">'+parseFloat((o.stallPrice).toFixed(5))+'</td></tr>'
				      			}); 
							   	layer.close(index);
							   	 $("#tablecontent2").html(html); 
							   	 self.loadEvents2();
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				  })
			  }
			  
			  this.loadPagination3 = function(data3){
				  //绣花定位激光
				  $(".profile2").on('click',function(){
					  var index;
					    var html = '';
					    $.ajax({
						      url:"${ctx}/product/getOrdinaryLaser",
						      data:data3,
						      type:"GET",
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			$("#ntwo2").val(result.data.rows[0].oneCutPrice)
				      			 $(result.data.rows).each(function(i,o){
				      				 html +='<tr>'
				      				+'<td class="text-center edit ">'+o.tailorName+'</td>'
				      				+'<td class="text-center tailorType3" data-tailortypeid2='+o.tailorTypeId+'></td>'
				      				+'<td class="text-center"><input class="form-control perimeter3" data-id="'+o.id+'" style="width: 80px;" value='+(o.perimeter!=0?o.perimeter:"")+'></td>'
				      				+'<td class="text-center edit"><input class="form-control stallPoint3" data-id="'+o.id+'" style="width: 50px;" value='+(o.stallPoint!=0?o.stallPoint:"")+'></td>'
				      				+'<td class="text-center edit"><select class="form-control singleDouble3" data-id="'+o.id+'" data-singledouble="'+o.singleDouble+'" style="width: 100px;"><option value="3">请选择</option><option value="1">单</option><option value="2">双</option></select></td>'
				      				+'<td class="text-center"><input class="form-control time3" data-id="'+o.id+'" style="width: 50px;"  value='+(o.time!=null?o.time:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control embroiderTime3" data-id="'+o.id+'" style="width: 80px;" value='+(o.embroiderTime!=null?o.embroiderTime:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeTwo3" data-id="'+o.id+'" style="width: 80px;" value='+(o.otherTimeTwo!=null?o.otherTimeTwo:"")+'></td>'
				      				+'<td class="text-center edit rabbTime3">'+parseFloat((o.rabbTime).toFixed(5))+'</td>'
				      				+'<td class="text-center edit singleLaserTime3">'+parseFloat((o.singleLaserTime).toFixed(5))+'</td>'
				      				+'<td class="text-center edit singleLaserHandTime3">'+parseFloat((o.singleLaserHandTime).toFixed(5))+'</td>'
				      				+'<td class="text-center edit labourCost3">'+parseFloat((o.labourCost).toFixed(8))+'</td>'
				      				+'<td class="text-center edit equipmentPrice3">'+parseFloat((o.equipmentPrice).toFixed(5))+'</td>'
				      				+'<td class="text-center edit administrativeAtaff3">'+parseFloat((o.administrativeAtaff).toFixed(5))+'</td>'
				      				+'<td class="text-center edit stallPrice3">'+parseFloat((o.stallPrice).toFixed(5))+'</td></tr>'
				      			}); 
							   	layer.close(index);
							   	 $("#tablecontent3").html(html); 
							   	 self.loadEvents3();
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				  })
			  }
			  
			  this.loadPagination4 = function(data4){
				  //冲床
				  $(".profile3").on('click',function(){
					  var index;
					    var html = '';
					    $.ajax({
						      url:"${ctx}/product/getOrdinaryLaser",
						      data:data4,
						      type:"GET",
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			$("#ntwo3").val(result.data.rows[0].oneCutPrice)
				      			 $(result.data.rows).each(function(i,o){
				      				 html +='<tr>'
				      				+'<td class="text-center edit ">'+o.tailorName+'</td>'
				      				+'<td class="text-center tailorType4" data-tailortypeid2='+o.tailorTypeId+'></td>'
				      				+'<td class="text-center"><input class="form-control layerNumber4" data-id="'+o.id+'" style="width: 85px;" value='+(o.layerNumber!=null?o.layerNumber:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeOne4" data-id="'+o.id+'" style="width: 85px;" value='+(o.otherTimeOne!=null?o.otherTimeOne:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeTwo4" data-id="'+o.id+'" style="width: 85px;" value='+(o.otherTimeTwo!=null?o.otherTimeTwo:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeThree4" data-id="'+o.id+'" style="width: 85px;" value='+(o.otherTimeThree!=null?o.otherTimeThree:"")+'></td>'
				      				+'<td class="text-center edit overlappedSeconds4">'+parseFloat((o.overlappedSeconds*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit punchingSeconds4">'+parseFloat((o.punchingSeconds*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit labourCost4">'+parseFloat((o.labourCost*1).toFixed(8))+'</td>'
				      				+'<td class="text-center edit equipmentPrice4">'+parseFloat((o.equipmentPrice*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit administrativeAtaff4">'+parseFloat((o.administrativeAtaff*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit stallPrice4">'+parseFloat((o.stallPrice*1).toFixed(5))+'</td></tr>'
				      			}); 
							   	layer.close(index);
							   	 $("#tablecontent4").html(html); 
							   	  self.loadEvents4(); 
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				  })
			  }
			  
			  this.loadPagination5 = function(data5){
				  //电烫
				  $(".profile4").on('click',function(){
					  var index;
					    var html = '';
					    $.ajax({
						      url:"${ctx}/product/getOrdinaryLaser",
						      data:data5,
						      type:"GET",
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			$("#ntwo4").val(result.data.rows[0].oneCutPrice)
				      			 $(result.data.rows).each(function(i,o){
				      				 html +='<tr>'
				      				+'<td class="text-center edit ">'+o.tailorName+'</td>'
				      				+'<td class="text-center tailorType5" data-tailortypeid2='+o.tailorTypeId+'></td>'
				      				+'<td class="text-center"><input class="form-control typesettingNumber5" data-id="'+o.id+'" style="width: 85px;" value='+(o.typesettingNumber!=null?o.typesettingNumber:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeOne5" data-id="'+o.id+'" style="width: 85px;" value='+(o.otherTimeOne!=null?o.otherTimeOne:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeTwo5" data-id="'+o.id+'" style="width: 85px;" value='+(o.otherTimeTwo!=null?o.otherTimeTwo:"")+'></td>'
				      				+'<td class="text-center edit permSeconds5">'+parseFloat((o.permSeconds*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit tearingSeconds5">'+parseFloat((o.tearingSeconds*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit rabbTime5">'+parseFloat((o.rabbTime*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit permPrice5">'+parseFloat((o.permPrice*1).toFixed(8))+'</td>'
				      				+'<td class="text-center edit tearingPrice5">'+parseFloat((o.tearingPrice*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit equipmentPrice5">'+parseFloat((o.equipmentPrice*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit administrativeAtaff5">'+parseFloat((o.administrativeAtaff*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit stallPrice5">'+parseFloat((o.stallPrice*1).toFixed(5))+'</td></tr>'
				      			}); 
							   	layer.close(index);
							   	 $("#tablecontent5").html(html); 
							   	   self.loadEvents5(); 
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				  })
			  }
			  
			  
			  this.loadPagination6 = function(data6){
				  //电推
				  $(".profile5").on('click',function(){
					  var index;
					    var html = '';
					    $.ajax({
						      url:"${ctx}/product/getOrdinaryLaser",
						      data:data6,
						      type:"GET",
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			$("#ntwo5").val(result.data.rows[0].oneCutPrice)
				      			 $(result.data.rows).each(function(i,o){
				      				 html +='<tr>'
				      				+'<td class="text-center edit ">'+o.tailorName+'</td>'
				      				+'<td class="text-center tailorType6" data-tailortypeid2='+o.tailorTypeId+'></td>'
				      				+'<td class="text-center"><input class="form-control layerNumber6" data-id="'+o.id+'" style="width: 85px;" value='+(o.layerNumber!=null?o.layerNumber:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control perimeter6" data-id="'+o.id+'" style="width: 85px;" value='+(o.perimeter!=0?o.perimeter:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeTwo6" data-id="'+o.id+'" style="width: 85px;" value='+(o.otherTimeTwo!=null?o.otherTimeTwo:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeThree6" data-id="'+o.id+'" style="width: 85px;" value='+(o.otherTimeThree!=null?o.otherTimeThree:"")+'></td>'
				      				+'<td class="text-center edit overlappedSeconds6">'+parseFloat((o.overlappedSeconds*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit electricSeconds6">'+parseFloat((o.electricSeconds*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit labourCost6">'+parseFloat((o.labourCost*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit equipmentPrice6">'+parseFloat((o.equipmentPrice*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit administrativeAtaff6">'+parseFloat((o.administrativeAtaff*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit stallPrice6">'+parseFloat((o.stallPrice*1).toFixed(5))+'</td></tr>'
				      			}); 
							   	layer.close(index);
							   	 $("#tablecontent6").html(html); 
							   	   self.loadEvents6(); 
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				  })
			  }
			  
			  this.loadPagination7 = function(data7){
				  //手工剪刀
				  $(".profile6").on('click',function(){
					  var index;
					    var html = '';
					    $.ajax({
						      url:"${ctx}/product/getOrdinaryLaser",
						      data:data7,
						      type:"GET",
						      beforeSend:function(){
							 	  index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								  });
							  }, 
				      		  success: function (result) {
				      			$("#ntwo6").val(result.data.rows[0].oneCutPrice)
				      			 $(result.data.rows).each(function(i,o){
				      				 html +='<tr>'
				      				+'<td class="text-center edit ">'+o.tailorName+'</td>'
				      				+'<td class="text-center tailorType7" data-tailortypeid2='+o.tailorTypeId+'></td>'
				      				+'<td class="text-center"><input class="form-control perimeter7" data-id="'+o.id+'" style="width: 85px;" value='+(o.perimeter!=0?o.perimeter:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeTwo7 " data-id="'+o.id+'" style="width: 85px;" value='+(o.otherTimeTwo!=null?o.otherTimeTwo:"")+'></td>'
				      				+'<td class="text-center"><input class="form-control otherTimeThree7" data-id="'+o.id+'" style="width: 85px;" value='+(o.otherTimeThree!=null?o.otherTimeThree:"")+'></td>'
				      				+'<td class="text-center edit manualSeconds7">'+parseFloat((o.manualSeconds*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit labourCost7">'+parseFloat((o.labourCost*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit equipmentPrice7">'+parseFloat((o.equipmentPrice*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit administrativeAtaff7">'+parseFloat((o.administrativeAtaff*1).toFixed(5))+'</td>'
				      				+'<td class="text-center edit stallPrice7">'+parseFloat((o.stallPrice*1).toFixed(5))+'</td></tr>'
				      			}); 
							   	layer.close(index);
							   	 $("#tablecontent7").html(html); 
							   	   self.loadEvents7(); 
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				  })
			  }
			  
			  this.loadEvents7 = function(){
				  //调用基础数据
				  var data2 = {
							type:"manual",
						}
				  var index;
				  $.ajax({
				      url:"${ctx}/product/getPrimeCoefficient",
				      data:data2,
				      type:"GET",
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      			$("#ordid6").val(o.id)
		      			$("#manualOne6").val(o.manualOne)
		      			$("#time6").val(o.time)
		      			$("#quickWorker6").val(o.quickWorker)
		      			$("#worth6").val(o.worth)
		      			$("#depreciation6").val(o.depreciation)
		      			$("#shareDay6").val(o.shareDay)
		      			$("#workTime6").val(o.workTime)
		      			$("#laserTubePrice6").val(o.laserTubePrice)
		      			$("#laserTubePriceSecond6").val(o.laserTubePriceSecond)
		      			$("#shareTime6").val(o.shareTime)
		      			$("#maintenanceChargeSecond6").val(o.maintenanceChargeSecond)
		      			$("#maintenanceCharge6").val(o.maintenanceCharge)
		      			$("#shareTimeTwo6").val(o.shareTimeTwo)
		      			$("#omnHorElectric6").val(o.omnHorElectric)
		      			$("#omnHorWater6").val(o.omnHorWater)
		      			$("#perSecondPrice6").val(o.perSecondPrice)
		      			$("#omnHorHouse6").val(o.omnHorHouse)
		      			$("#omnHorMachinist6").val(o.omnHorMachinist)
		      			$("#perSecondMachinist6").val(o.perSecondMachinist)
		      			$("#omnHorAuxiliary6").val(o.omnHorAuxiliary)
		      			$("#perSecondMachinistTwo6").val(o.perSecondMachinistTwo)
		      			$("#managePrice6").val(o.managePrice)
		      			$("#perSecondManage6").val(o.perSecondManage)
		      			$("#manageEquipmentNumber6").val(o.manageEquipmentNumber)
		      			$("#equipmentProfit6").val(o.equipmentProfit)
		      			$("#embroideryLaserNumber6").val(o.embroideryLaserNumber)
		      			$("#perimeterLess6").val(o.perimeterLess)
		      			$("#perimeterLessNumber6").val(o.perimeterLessNumber)
		      			 }); 
		      		  },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
				  $(".updateord").on('click',function(){
					  var data={
					  	id:$("#ordid6").val(),
					  	extent:12,
					  	manualOne:$("#manualOne").val(),
					  	quickWorker:$("#quickWorker6").val(),
					  	worth:$("#worth6").val(),
					  	shareDay:$("#shareDay6").val(),
					  	workTime:$("#workTime6").val(),
					  	laserTubePrice:$("#laserTubePrice6").val(),
					  	shareTime:$("#shareTime6").val(),
					  	maintenanceCharge:$("#maintenanceCharge6").val(),
					  	shareTimeTwo:$("#shareTimeTwo6").val(),
					  	omnHorElectric:$("#omnHorElectric6").val(),
					  	omnHorWater:$("#omnHorWater6").val(),
					  	omnHorHouse:$("#omnHorHouse6").val(),
					  	omnHorMachinist:$("#omnHorMachinist6").val(),
					  	omnHorAuxiliary:$("#omnHorAuxiliary6").val(),
					  	managePrice:$("#managePrice6").val(),
					  	manageEquipmentNumber:$("#manageEquipmentNumber6").val(),
					  	equipmentProfit:$("#equipmentProfit6").val(),
					  	perimeterLessNumber:$("#perimeterLessNumber6").val(),
					  	perimeterLess:$("#perimeterLess6").val(),
					  	embroideryLaserNumber:$("#embroideryLaserNumber6").val(),
					  }
					  $.ajax({
					      url:"${ctx}/product/updatePrimeCoefficient",
					      data:data,
					      type:"POST",
			      		  success: function (result) {
			      			 $(result.data).each(function(i,o){
			      				$("#time6").val(o.time)
			      				$("#depreciation6").val(o.depreciation)
			      				$("#laserTubePriceSecond6").val(o.laserTubePriceSecond)
			      				$("#maintenanceChargeSecond6").val(o.maintenanceChargeSecond)
			      				$("#perSecondPrice6").val(o.perSecondPrice)
			      				$("#perSecondMachinist6").val(o.perSecondMachinist)
			      				$("#perSecondManage6").val(o.perSecondManage)
			      				$("#perSecondMachinistTwo6").val(o.perSecondMachinistTwo)
			      				$("#time6").val(o.time)
			      				if(0==result.code){
									layer.msg(result.message, {icon: 1});
									layer.close(index);
									}else{
										layer.msg(result.message, {icon: 2});
										layer.close(index);
									}
			      			}); 
			      		  },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);sa
						  }
					  });
				  })
				  //遍历裁剪方式
				  var data = {
							type:"tailor",
						}
						var index;
					    var html = '';
					    var htmlto= '';
					    $.ajax({
						      url:"${ctx}/product/getBaseOne",
						      data:data,
						      type:"GET",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      				html +='<option value="'+o.id+'">'+o.name+'</option>'
				      			}); 
						       htmlto='<select class="text-center form-control selecttailorType2" disabled="disabled"><option value="">请选择</option>'+html+'</select>'
				      		  $(".tailorType7").html(htmlto)
				      		  //改变事件
				      		  $(".selecttailorType2").each(function(i,o){
				      				var id=	$(o).parent().data("tailortypeid2");
									$(o).val(id)
								}) 
				      		  },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);sa
							  }
						  });
					    $('.perimeter7').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		perimeter:$(this).parent().parent().find(".perimeter7").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo7").val(),
					    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree7").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".manualSeconds7").text(parseFloat((o.manualSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost7").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice7").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff7").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice7").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    
					    $('.otherTimeTwo7').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		perimeter:$(this).parent().parent().find(".perimeter7").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo7").val(),
						    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree7").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".manualSeconds7").text(parseFloat((o.manualSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost7").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice7").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff7").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice7").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeThree7').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		perimeter:$(this).parent().parent().find(".perimeter7").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo7").val(),
						    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree7").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".manualSeconds7").text(parseFloat((o.manualSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost7").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice7").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff7").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice7").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
			  }
			  
			  
			  this.loadEvents6 = function(){
				  //调用基础数据
				  var data2 = {
							type:"electricPush",
						}
				  var index;
				  $.ajax({
				      url:"${ctx}/product/getPrimeCoefficient",
				      data:data2,
				      type:"GET",
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      			$("#ordid5").val(o.id)
		      			$("#electricPushOne5").val(o.electricPushOne)
		      			$("#electricPushTwo5").val(o.electricPushTwo)
		      			$("#electricPushThree5").val(o.electricPushThree)
		      			$("#electricPushFour5").val(o.electricPushFour)
		      			$("#electricPushFive5").val(o.electricPushFive)
		      			$("#time5").val(o.time)
		      			$("#quickWorker5").val(o.quickWorker)
		      			$("#worth5").val(o.worth)
		      			$("#depreciation5").val(o.depreciation)
		      			$("#shareDay5").val(o.shareDay)
		      			$("#workTime5").val(o.workTime)
		      			$("#laserTubePrice5").val(o.laserTubePrice)
		      			$("#laserTubePriceSecond5").val(o.laserTubePriceSecond)
		      			$("#shareTime5").val(o.shareTime)
		      			$("#maintenanceChargeSecond5").val(o.maintenanceChargeSecond)
		      			$("#maintenanceCharge5").val(o.maintenanceCharge)
		      			$("#shareTimeTwo5").val(o.shareTimeTwo)
		      			$("#omnHorElectric5").val(o.omnHorElectric)
		      			$("#omnHorWater5").val(o.omnHorWater)
		      			$("#perSecondPrice5").val(o.perSecondPrice)
		      			$("#omnHorHouse5").val(o.omnHorHouse)
		      			$("#omnHorMachinist5").val(o.omnHorMachinist)
		      			$("#perSecondMachinist5").val(o.perSecondMachinist)
		      			$("#omnHorAuxiliary5").val(o.omnHorAuxiliary)
		      			$("#perSecondMachinistTwo5").val(o.perSecondMachinistTwo)
		      			$("#managePrice5").val(o.managePrice)
		      			$("#perSecondManage5").val(o.perSecondManage)
		      			$("#manageEquipmentNumber5").val(o.manageEquipmentNumber)
		      			$("#equipmentProfit5").val(o.equipmentProfit)
		      			$("#embroideryLaserNumber5").val(o.embroideryLaserNumber)
		      			$("#perimeterLess5").val(o.perimeterLess)
		      			$("#perimeterLessNumber5").val(o.perimeterLessNumber)
		      			 }); 
		      		  },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
				  $(".updateord").on('click',function(){
					  var data={
					  	id:$("#ordid5").val(),
					  	extent:12,
					  	electricPushOne:$("#electricPushOne5").val(),
					  	electricPushTwo:$("#electricPushTwo5").val(),
					  	electricPushThree:$("#electricPushThree5").val(),
					  	electricPushFour:$("#electricPushFour5").val(),
					  	electricPushFour:$("#electricPushFour5").val(),
					  	electricPushFive:$("#electricPushFive5").val(),
					  	quickWorker:$("#quickWorker5").val(),
					  	worth:$("#worth5").val(),
					  	shareDay:$("#shareDay5").val(),
					  	workTime:$("#workTime5").val(),
					  	laserTubePrice:$("#laserTubePrice5").val(),
					  	shareTime:$("#shareTime5").val(),
					  	maintenanceCharge:$("#maintenanceCharge5").val(),
					  	shareTimeTwo:$("#shareTimeTwo5").val(),
					  	omnHorElectric:$("#omnHorElectric5").val(),
					  	omnHorWater:$("#omnHorWater5").val(),
					  	omnHorHouse:$("#omnHorHouse5").val(),
					  	omnHorMachinist:$("#omnHorMachinist5").val(),
					  	omnHorAuxiliary:$("#omnHorAuxiliary5").val(),
					  	managePrice:$("#managePrice5").val(),
					  	manageEquipmentNumber:$("#manageEquipmentNumber5").val(),
					  	equipmentProfit:$("#equipmentProfit5").val(),
					  	perimeterLessNumber:$("#perimeterLessNumber5").val(),
					  	perimeterLess:$("#perimeterLess5").val(),
					  	embroideryLaserNumber:$("#embroideryLaserNumber5").val(),
					  }
					  $.ajax({
					      url:"${ctx}/product/updatePrimeCoefficient",
					      data:data,
					      type:"POST",
			      		  success: function (result) {
			      			 $(result.data).each(function(i,o){
			      				$("#time5").val(o.time)
			      				$("#depreciation5").val(o.depreciation)
			      				$("#laserTubePriceSecond5").val(o.laserTubePriceSecond)
			      				$("#maintenanceChargeSecond5").val(o.maintenanceChargeSecond)
			      				$("#perSecondPrice5").val(o.perSecondPrice)
			      				$("#perSecondMachinist5").val(o.perSecondMachinist)
			      				$("#perSecondManage5").val(o.perSecondManage)
			      				$("#perSecondMachinistTwo5").val(o.perSecondMachinistTwo)
			      				$("#time5").val(o.time)
			      				if(0==result.code){
									layer.msg(result.message, {icon: 1});
									layer.close(index);
									}else{
										layer.msg(result.message, {icon: 2});
										layer.close(index);
									}
			      			}); 
			      		  },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);sa
						  }
					  });
				  })
				  //遍历裁剪方式
				  var data = {
							type:"tailor",
						}
						var index;
					    var html = '';
					    var htmlto= '';
					    $.ajax({
						      url:"${ctx}/product/getBaseOne",
						      data:data,
						      type:"GET",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      				html +='<option value="'+o.id+'">'+o.name+'</option>'
				      			}); 
						       htmlto='<select class="text-center form-control selecttailorType2" disabled="disabled"><option value="">请选择</option>'+html+'</select>'
				      		  $(".tailorType6").html(htmlto)
				      		  //改变事件
				      		  $(".selecttailorType2").each(function(i,o){
				      				var id=	$(o).parent().data("tailortypeid2");
									$(o).val(id)
								}) 
				      		  },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);sa
							  }
						  });
					    $('.layerNumber6').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		layerNumber:$(this).val(),
					    		perimeter:$(this).parent().parent().find(".perimeter6").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo6").val(),
					    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree6").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".overlappedSeconds6").text(parseFloat((o.overlappedSeconds).toFixed(5)));
					      				that.parent().parent().find(".electricSeconds6").text(parseFloat((o.electricSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost6").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice6").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff6").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice6").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.perimeter6').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		layerNumber:$(this).parent().parent().find(".layerNumber6").val(),
						    		perimeter:$(this).parent().parent().find(".perimeter6").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo6").val(),
						    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree6").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".overlappedSeconds6").text(parseFloat((o.overlappedSeconds).toFixed(5)));
					      				that.parent().parent().find(".electricSeconds6").text(parseFloat((o.electricSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost6").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice6").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff6").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice6").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeTwo6').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		layerNumber:$(this).parent().parent().find(".layerNumber6").val(),
						    		perimeter:$(this).parent().parent().find(".perimeter6").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo6").val(),
						    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree6").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".overlappedSeconds6").text(parseFloat((o.overlappedSeconds).toFixed(5)));
					      				that.parent().parent().find(".electricSeconds6").text(parseFloat((o.electricSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost6").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice6").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff6").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice6").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeThree6').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		layerNumber:$(this).parent().parent().find(".layerNumber6").val(),
						    		perimeter:$(this).parent().parent().find(".perimeter6").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo6").val(),
						    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree6").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".overlappedSeconds6").text(parseFloat((o.overlappedSeconds).toFixed(5)));
					      				that.parent().parent().find(".electricSeconds6").text(parseFloat((o.electricSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost6").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice6").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff6").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice6").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
			  }
			  
			  
			  
			  this.loadEvents5 = function(){
				  //调用基础数据
				  var data2 = {
							type:"perm",
						}
				  var index;
				  $.ajax({
				      url:"${ctx}/product/getPrimeCoefficient",
				      data:data2,
				      type:"GET",
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      			$("#ordid4").val(o.id)
		      			$("#permOne4").val(o.puncherOne)
		      			$("#permTwo4").val(o.puncherTwo)
		      			$("#permThree4").val(o.puncherThree)
		      			$("#permFour4").val(o.puncherFour)
		      			$("#permFive4").val(o.puncherFive)
		      			$("#quickWorker4").val(o.quickWorker)
		      			$("#worth4").val(o.worth)
		      			$("#depreciation4").val(o.depreciation)
		      			$("#shareDay4").val(o.shareDay)
		      			$("#workTime4").val(o.workTime)
		      			$("#laserTubePrice4").val(o.laserTubePrice)
		      			$("#laserTubePriceSecond4").val(o.laserTubePriceSecond)
		      			$("#shareTime4").val(o.shareTime)
		      			$("#maintenanceChargeSecond4").val(o.maintenanceChargeSecond)
		      			$("#maintenanceCharge4").val(o.maintenanceCharge)
		      			$("#shareTimeTwo4").val(o.shareTimeTwo)
		      			$("#omnHorElectric4").val(o.omnHorElectric)
		      			$("#omnHorWater4").val(o.omnHorWater)
		      			$("#perSecondPrice4").val(o.perSecondPrice)
		      			$("#omnHorHouse4").val(o.omnHorHouse)
		      			$("#omnHorMachinist4").val(o.omnHorMachinist)
		      			$("#perSecondMachinist4").val(o.perSecondMachinist)
		      			$("#omnHorAuxiliary4").val(o.omnHorAuxiliary)
		      			$("#perSecondMachinistTwo4").val(o.perSecondMachinistTwo)
		      			$("#managePrice4").val(o.managePrice)
		      			$("#perSecondManage4").val(o.perSecondManage)
		      			$("#manageEquipmentNumber4").val(o.manageEquipmentNumber)
		      			$("#equipmentProfit4").val(o.equipmentProfit)
		      			$("#embroideryLaserNumber4").val(o.embroideryLaserNumber)
		      			$("#perimeterLess4").val(o.perimeterLess)
		      			$("#perimeterLessNumber4").val(o.perimeterLessNumber)
		      			 }); 
		      		  },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
				  $(".updateord").on('click',function(){
					  var data={
					  	id:$("#ordid4").val(),
					  	puncherOne:$("#permOne4").val(),
					  	puncherTwo:$("#permTwo4").val(),
					  	puncherThree:$("#permThree4").val(),
					  	puncherFour:$("#permFour4").val(),
					  	puncherFour:$("#permFour4").val(),
					  	puncherFive:$("#permFive4").val(),
					  	quickWorker:$("#quickWorker4").val(),
					  	worth:$("#worth4").val(),
					  	shareDay:$("#shareDay4").val(),
					  	workTime:$("#workTime4").val(),
					  	laserTubePrice:$("#laserTubePrice4").val(),
					  	shareTime:$("#shareTime4").val(),
					  	maintenanceCharge:$("#maintenanceCharge4").val(),
					  	shareTimeTwo:$("#shareTimeTwo4").val(),
					  	omnHorElectric:$("#omnHorElectric4").val(),
					  	omnHorWater:$("#omnHorWater4").val(),
					  	omnHorHouse:$("#omnHorHouse4").val(),
					  	omnHorMachinist:$("#omnHorMachinist4").val(),
					  	omnHorAuxiliary:$("#omnHorAuxiliary4").val(),
					  	managePrice:$("#managePrice4").val(),
					  	manageEquipmentNumber:$("#manageEquipmentNumber4").val(),
					  	equipmentProfit:$("#equipmentProfit4").val(),
					  	perimeterLessNumber:$("#perimeterLessNumber4").val(),
					  	perimeterLess:$("#perimeterLess4").val(),
					  	embroideryLaserNumber:$("#embroideryLaserNumber4").val(),
					  }
					  $.ajax({
					      url:"${ctx}/product/updatePrimeCoefficient",
					      data:data,
					      type:"POST",
			      		  success: function (result) {
			      			 $(result.data).each(function(i,o){
			      				$("#time4").val(o.time)
			      				$("#depreciation4").val(o.depreciation)
			      				$("#laserTubePriceSecond4").val(o.laserTubePriceSecond)
			      				$("#maintenanceChargeSecond4").val(o.maintenanceChargeSecond)
			      				$("#perSecondPrice4").val(o.perSecondPrice)
			      				$("#perSecondMachinist4").val(o.perSecondMachinist)
			      				$("#perSecondManage4").val(o.perSecondManage)
			      				$("#perSecondMachinistTwo4").val(o.perSecondMachinistTwo)
			      				if(0==result.code){
									layer.msg(result.message, {icon: 1});
									layer.close(index);
									}else{
										layer.msg(result.message, {icon: 2});
										layer.close(index);
									}
			      			}); 
			      		  },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);sa
						  }
					  });
				  })
				  //遍历裁剪方式
				  var data = {
							type:"tailor",
						}
						var index;
					    var html = '';
					    var htmlto= '';
					    $.ajax({
						      url:"${ctx}/product/getBaseOne",
						      data:data,
						      type:"GET",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      				html +='<option value="'+o.id+'">'+o.name+'</option>'
				      			}); 
						       htmlto='<select class="text-center form-control selecttailorType2" disabled="disabled"><option value="">请选择</option>'+html+'</select>'
				      		  $(".tailorType5").html(htmlto)
				      		  //改变事件
				      		  $(".selecttailorType2").each(function(i,o){
				      				var id=	$(o).parent().data("tailortypeid2");
									$(o).val(id)
								}) 
				      		  },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);sa
							  }
						  });
					    $('.typesettingNumber5').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		typesettingNumber:$(this).val(),
					    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne5").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo5").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".permPrice5").text(parseFloat((o.permPrice).toFixed(5)));
					      				that.parent().parent().find(".tearingPrice5").text(parseFloat((o.tearingPrice).toFixed(5)));
					      				that.parent().parent().find(".permSeconds5").text(parseFloat((o.permSeconds).toFixed(5)));
					      				that.parent().parent().find(".tearingSeconds5").text(parseFloat((o.tearingSeconds).toFixed(5)));
					      				that.parent().parent().find(".rabbTime5").text(parseFloat((o.rabbTime).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice5").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff5").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice5").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeOne5').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		typesettingNumber:$(this).parent().parent().find(".typesettingNumber5").val(),
						    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne5").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo5").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".permPrice5").text(parseFloat((o.permPrice).toFixed(5)));
					      				that.parent().parent().find(".tearingPrice5").text(parseFloat((o.tearingPrice).toFixed(5)));
					      				that.parent().parent().find(".permSeconds5").text(parseFloat((o.permSeconds).toFixed(5)));
					      				that.parent().parent().find(".tearingSeconds5").text(parseFloat((o.tearingSeconds).toFixed(5)));
					      				that.parent().parent().find(".rabbTime5").text(parseFloat((o.rabbTime).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice5").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff5").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice5").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeTwo5').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		typesettingNumber:$(this).parent().parent().find(".typesettingNumber5").val(),
						    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne5").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo5").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".permPrice5").text(parseFloat((o.permPrice).toFixed(5)));
					      				that.parent().parent().find(".tearingPrice5").text(parseFloat((o.tearingPrice).toFixed(5)));
					      				that.parent().parent().find(".permSeconds5").text(parseFloat((o.permSeconds).toFixed(5)));
					      				that.parent().parent().find(".tearingSeconds5").text(parseFloat((o.tearingSeconds).toFixed(5)));
					      				that.parent().parent().find(".rabbTime5").text(parseFloat((o.rabbTime).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice5").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff5").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice5").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
			  }
			  
			  
			  this.loadEvents4 = function(){
				  //调用基础数据
				  var data2 = {
							type:"puncher",
						}
				  var index;
				  $.ajax({
				      url:"${ctx}/product/getPrimeCoefficient",
				      data:data2,
				      type:"GET",
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      			$("#ordid3").val(o.id)
		      			$("#puncherOne3").val(o.puncherOne)
		      			$("#puncherTwo3").val(o.puncherTwo)
		      			$("#puncherThree3").val(o.puncherThree)
		      			$("#puncherFour3").val(o.puncherFour)
		      			$("#puncherFive3").val(o.puncherFive)
		      			$("#quickWorker3").val(o.quickWorker)
		      			$("#worth3").val(o.worth)
		      			$("#depreciation3").val(o.depreciation)
		      			$("#shareDay3").val(o.shareDay)
		      			$("#workTime3").val(o.workTime)
		      			$("#laserTubePrice3").val(o.laserTubePrice)
		      			$("#laserTubePriceSecond3").val(o.laserTubePriceSecond)
		      			$("#shareTime3").val(o.shareTime)
		      			$("#maintenanceChargeSecond3").val(o.maintenanceChargeSecond)
		      			$("#maintenanceCharge3").val(o.maintenanceCharge)
		      			$("#shareTimeTwo3").val(o.shareTimeTwo)
		      			$("#omnHorElectric3").val(o.omnHorElectric)
		      			$("#omnHorWater3").val(o.omnHorWater)
		      			$("#perSecondPrice3").val(o.perSecondPrice)
		      			$("#omnHorHouse3").val(o.omnHorHouse)
		      			$("#omnHorMachinist3").val(o.omnHorMachinist)
		      			$("#perSecondMachinist3").val(o.perSecondMachinist)
		      			$("#managePrice3").val(o.managePrice)
		      			$("#perSecondManage3").val(o.perSecondManage)
		      			$("#manageEquipmentNumber3").val(o.manageEquipmentNumber)
		      			$("#equipmentProfit3").val(o.equipmentProfit)
		      			$("#embroideryLaserNumber3").val(o.embroideryLaserNumber)
		      			$("#perimeterLess3").val(o.perimeterLess)
		      			$("#perimeterLessNumber3").val(o.perimeterLessNumber)
		      			 }); 
		      		  },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
				  $(".updateord").on('click',function(){
					  var data={
					  	id:$("#ordid3").val(),
					  	puncherOne:$("#puncherOne3").val(),
					  	puncherTwo:$("#puncherTwo3").val(),
					  	puncherThree:$("#puncherThree3").val(),
					  	puncherFour:$("#puncherFour3").val(),
					  	puncherFour:$("#puncherFour3").val(),
					  	puncherFive:$("#puncherFive3").val(),
					  	quickWorker:$("#quickWorker3").val(),
					  	worth:$("#worth3").val(),
					  	shareDay:$("#shareDay3").val(),
					  	workTime:$("#workTime3").val(),
					  	laserTubePrice:$("#laserTubePrice3").val(),
					  	shareTime:$("#shareTime3").val(),
					  	maintenanceCharge:$("#maintenanceCharge3").val(),
					  	shareTimeTwo:$("#shareTimeTwo3").val(),
					  	omnHorElectric:$("#omnHorElectric3").val(),
					  	omnHorWater:$("#omnHorWater3").val(),
					  	omnHorHouse:$("#omnHorHouse3").val(),
					  	omnHorMachinist:$("#omnHorMachinist3").val(),
					  	managePrice:$("#managePrice3").val(),
					  	manageEquipmentNumber:$("#manageEquipmentNumber3").val(),
					  	equipmentProfit:$("#equipmentProfit3").val(),
					  	perimeterLessNumber:$("#perimeterLessNumber3").val(),
					  	perimeterLess:$("#perimeterLess3").val(),
					  	embroideryLaserNumber:$("#embroideryLaserNumber3").val(),
					  }
					  $.ajax({
					      url:"${ctx}/product/updatePrimeCoefficient",
					      data:data,
					      type:"POST",
			      		  success: function (result) {
			      			 $(result.data).each(function(i,o){
			      				$("#time3").val(o.time)
			      				$("#depreciation3").val(o.depreciation)
			      				$("#laserTubePriceSecond3").val(o.laserTubePriceSecond)
			      				$("#maintenanceChargeSecond3").val(o.maintenanceChargeSecond)
			      				$("#perSecondPrice3").val(o.perSecondPrice)
			      				$("#perSecondMachinist3").val(o.perSecondMachinist)
			      				$("#perSecondManage3").val(o.perSecondManage)
			      				if(0==result.code){
									layer.msg(result.message, {icon: 1});
									layer.close(index);
									}else{
										layer.msg(result.message, {icon: 2});
										layer.close(index);
									}
			      			}); 
			      		  },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);sa
						  }
					  });
				  })
				  //遍历裁剪方式
				  var data = {
							type:"tailor",
						}
						var index;
					    var html = '';
					    var htmlto= '';
					    $.ajax({
						      url:"${ctx}/product/getBaseOne",
						      data:data,
						      type:"GET",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      				html +='<option value="'+o.id+'">'+o.name+'</option>'
				      			}); 
						       htmlto='<select class="text-center form-control selecttailorType2" disabled="disabled"><option value="">请选择</option>'+html+'</select>'
				      		  $(".tailorType4").html(htmlto)
				      		  //改变事件
				      		  $(".selecttailorType2").each(function(i,o){
				      				var id=	$(o).parent().data("tailortypeid2");
									$(o).val(id)
								}) 
				      		  },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);sa
							  }
						  });
					    $('.layerNumber4').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		layerNumber:$(this).val(),
					    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne4").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo4").val(),
					    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree4").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".overlappedSeconds4").text(parseFloat((o.overlappedSeconds).toFixed(5)));
					      				that.parent().parent().find(".punchingSeconds4").text(parseFloat((o.punchingSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost4").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice4").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff4").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice4").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeOne4').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		layerNumber:$(this).parent().parent().find(".layerNumber4").val(),
						    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne4").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo4").val(),
						    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree4").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".overlappedSeconds4").text(parseFloat((o.overlappedSeconds).toFixed(5)));
					      				that.parent().parent().find(".punchingSeconds4").text(parseFloat((o.punchingSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost4").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice4").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff4").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice4").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeTwo4').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		layerNumber:$(this).parent().parent().find(".layerNumber4").val(),
						    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne4").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo4").val(),
						    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree4").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".overlappedSeconds4").text(parseFloat((o.overlappedSeconds).toFixed(5)));
					      				that.parent().parent().find(".punchingSeconds4").text(parseFloat((o.punchingSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost4").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice4").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff4").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice4").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeThree4').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		layerNumber:$(this).parent().parent().find(".layerNumber4").val(),
						    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne4").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo4").val(),
						    		otherTimeThree:$(this).parent().parent().find(".otherTimeThree4").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".overlappedSeconds4").text(parseFloat((o.overlappedSeconds).toFixed(5)));
					      				that.parent().parent().find(".punchingSeconds4").text(parseFloat((o.punchingSeconds).toFixed(5)));
					      				that.parent().parent().find(".labourCost4").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice4").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff4").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice4").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
			  }
			  
			  this.loadEvents3 = function(){
				  //调用基础数据
				  var data2 = {
							type:"embroideryLaser",
						}
				  var index;
				  $.ajax({
				      url:"${ctx}/product/getPrimeCoefficient",
				      data:data2,
				      type:"GET",
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      			$("#ordid2").val(o.id)
		      			$("#peripheralLaser2").val(o.peripheralLaser)
		      			$("#extent2").val(o.extent)
		      			$("#time2").val(o.time)
		      			$("#pauseTime2").val(o.pauseTime)
		      			$("#rabbTime2").val(o.rabbTime)
		      			$("#quilt2").val(o.quilt)
		      			$("#quickWorker2").val(o.quickWorker)
		      			$("#worth2").val(o.worth)
		      			$("#depreciation2").val(o.depreciation)
		      			$("#shareDay2").val(o.shareDay)
		      			$("#workTime2").val(o.workTime)
		      			$("#laserTubePrice2").val(o.laserTubePrice)
		      			$("#laserTubePriceSecond2").val(o.laserTubePriceSecond)
		      			$("#shareTime2").val(o.shareTime)
		      			$("#maintenanceChargeSecond2").val(o.maintenanceChargeSecond)
		      			$("#maintenanceCharge2").val(o.maintenanceCharge)
		      			$("#shareTimeTwo2").val(o.shareTimeTwo)
		      			$("#omnHorElectric2").val(o.omnHorElectric)
		      			$("#omnHorWater2").val(o.omnHorWater)
		      			$("#perSecondPrice2").val(o.perSecondPrice)
		      			$("#omnHorHouse2").val(o.omnHorHouse)
		      			$("#omnHorMachinist2").val(o.omnHorMachinist)
		      			$("#perSecondMachinist2").val(o.perSecondMachinist)
		      			$("#managePrice2").val(o.managePrice)
		      			$("#perSecondManage2").val(o.perSecondManage)
		      			$("#manageEquipmentNumber2").val(o.manageEquipmentNumber)
		      			$("#equipmentProfit2").val(o.equipmentProfit)
		      			$("#embroideryLaserNumber2").val(o.embroideryLaserNumber)
		      			$("#perimeterLess2").val(o.perimeterLess)
		      			$("#perimeterLessNumber2").val(o.perimeterLessNumber)
		      			 }); 
		      		  },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
				  $(".updateord").on('click',function(){
					  var data={
					  	id:$("#ordid2").val(),
					  	peripheralLaser:$("#peripheralLaser2").val(),
					  	extent:$("#extent2").val(),
					  	pauseTime:$("#pauseTime2").val(),
					  	rabbTime:$("#rabbTime2").val(),
					  	quilt:$("#quilt2").val(),
					  	quickWorker:$("#quickWorker2").val(),
					  	worth:$("#worth2").val(),
					  	shareDay:$("#shareDay2").val(),
					  	workTime:$("#workTime2").val(),
					  	laserTubePrice:$("#laserTubePrice2").val(),
					  	shareTime:$("#shareTime2").val(),
					  	maintenanceCharge:$("#maintenanceCharge2").val(),
					  	shareTimeTwo:$("#shareTimeTwo2").val(),
					  	omnHorElectric:$("#omnHorElectric2").val(),
					  	omnHorWater:$("#omnHorWater2").val(),
					  	omnHorHouse:$("#omnHorHouse2").val(),
					  	omnHorMachinist:$("#omnHorMachinist2").val(),
					  	managePrice:$("#managePrice2").val(),
					  	manageEquipmentNumber:$("#manageEquipmentNumber2").val(),
					  	equipmentProfit:$("#equipmentProfit2").val(),
					  	perimeterLessNumber:$("#perimeterLessNumber2").val(),
					  	perimeterLess:$("#perimeterLess2").val(),
					  	embroideryLaserNumber:$("#embroideryLaserNumber2").val(),
					  }
					  $.ajax({
					      url:"${ctx}/product/updatePrimeCoefficient",
					      data:data,
					      type:"POST",
			      		  success: function (result) {
			      			 $(result.data).each(function(i,o){
			      				$("#time2").val(o.time)
			      				$("#depreciation2").val(o.depreciation)
			      				$("#laserTubePriceSecond2").val(o.laserTubePriceSecond)
			      				$("#maintenanceChargeSecond2").val(o.maintenanceChargeSecond)
			      				$("#perSecondPrice2").val(o.perSecondPrice)
			      				$("#perSecondMachinist2").val(o.perSecondMachinist)
			      				$("#perSecondManage2").val(o.perSecondManage)
			      				if(0==result.code){
									layer.msg(result.message, {icon: 1});
									layer.close(index);
									}else{
										layer.msg(result.message, {icon: 2});
										layer.close(index);
									}
			      			}); 
			      		  },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);sa
						  }
					  });
				  })
				  //遍历裁剪方式
				  var data = {
							type:"tailor",
						}
						var index;
					    var html = '';
					    var htmlto= '';
					    $.ajax({
						      url:"${ctx}/product/getBaseOne",
						      data:data,
						      type:"GET",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      				html +='<option value="'+o.id+'">'+o.name+'</option>'
				      			}); 
						       htmlto='<select class="text-center form-control selecttailorType2" disabled="disabled"><option value="">请选择</option>'+html+'</select>'
				      		  $(".tailorType3").html(htmlto)
				      		  //改变事件
				      		  $(".selecttailorType2").each(function(i,o){
				      				var id=	$(o).parent().data("tailortypeid2");
									$(o).val(id)
								}) 
				      		  },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);sa
							  }
						  });
					    $(".singleDouble3").each(function(j,k){
					      	var id=$(k).data("singledouble")
							$(k).val(id)
					}) 
					    $('.perimeter3').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		perimeter:$(this).val(),
					    		stallPoint:$(this).parent().parent().find(".stallPoint3").val(),
					    		singleDouble:$(this).parent().parent().find(".singleDouble3").val(),
					    		time:$(this).parent().parent().find(".time3").val(),
					    		embroiderTime:$(this).parent().parent().find(".embroiderTime3").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo3").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime3").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime3").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime3").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost3").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice3").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff3").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice3").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.stallPoint3').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		perimeter:$(this).parent().parent().find(".perimeter3").val(),
					    		stallPoint:$(this).parent().parent().find(".stallPoint3").val(),
					    		singleDouble:$(this).parent().parent().find(".singleDouble3").val(),
					    		time:$(this).parent().parent().find(".time3").val(),
					    		embroiderTime:$(this).parent().parent().find(".embroiderTime3").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo3").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime3").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime3").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime3").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost3").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice3").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff3").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice3").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.singleDouble3').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		perimeter:$(this).parent().parent().find(".perimeter3").val(),
						    		stallPoint:$(this).parent().parent().find(".stallPoint3").val(),
						    		singleDouble:$(this).parent().parent().find(".singleDouble3").val(),
						    		time:$(this).parent().parent().find(".time3").val(),
						    		embroiderTime:$(this).parent().parent().find(".embroiderTime3").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo3").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime3").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime3").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime3").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost3").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice3").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff3").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice3").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.time3').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		perimeter:$(this).parent().parent().find(".perimeter3").val(),
						    		stallPoint:$(this).parent().parent().find(".stallPoint3").val(),
						    		singleDouble:$(this).parent().parent().find(".singleDouble3").val(),
						    		time:$(this).parent().parent().find(".time3").val(),
						    		embroiderTime:$(this).parent().parent().find(".embroiderTime3").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo3").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime3").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime3").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime3").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost3").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice3").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff3").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice3").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.embroiderTime3').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		perimeter:$(this).parent().parent().find(".perimeter3").val(),
						    		stallPoint:$(this).parent().parent().find(".stallPoint3").val(),
						    		singleDouble:$(this).parent().parent().find(".singleDouble3").val(),
						    		time:$(this).parent().parent().find(".time3").val(),
						    		embroiderTime:$(this).parent().parent().find(".embroiderTime3").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo3").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime3").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime3").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime3").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost3").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice3").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff3").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice3").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeTwo3').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    			id:id,
						    		perimeter:$(this).parent().parent().find(".perimeter3").val(),
						    		stallPoint:$(this).parent().parent().find(".stallPoint3").val(),
						    		singleDouble:$(this).parent().parent().find(".singleDouble3").val(),
						    		time:$(this).parent().parent().find(".time3").val(),
						    		embroiderTime:$(this).parent().parent().find(".embroiderTime3").val(),
						    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo3").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime3").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime3").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime3").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost3").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice3").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff3").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice3").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
			  }
			  
			  
			  this.loadEvents2 = function(){
				  //调用基础数据
				  var data2 = {
							type:"ordinarylaser",
						}
				  var index;
				  $.ajax({
				      url:"${ctx}/product/getPrimeCoefficient",
				      data:data2,
				      type:"GET",
		      		  success: function (result) {
		      			 $(result.data).each(function(i,o){
		      			$("#ordid").val(o.id)
		      			$("#peripheralLaser").val(o.peripheralLaser)
		      			$("#extent").val(o.extent)
		      			$("#time").val(o.time)
		      			$("#pauseTime").val(o.pauseTime)
		      			$("#rabbTime").val(o.rabbTime)
		      			$("#quilt").val(o.quilt)
		      			$("#quickWorker").val(o.quickWorker)
		      			$("#worth").val(o.worth)
		      			$("#depreciation").val(o.depreciation)
		      			$("#shareDay").val(o.shareDay)
		      			$("#workTime").val(o.workTime)
		      			$("#laserTubePrice").val(o.laserTubePrice)
		      			$("#laserTubePriceSecond").val(o.laserTubePriceSecond)
		      			$("#shareTime").val(o.shareTime)
		      			$("#maintenanceChargeSecond").val(o.maintenanceChargeSecond)
		      			$("#maintenanceCharge").val(o.maintenanceCharge)
		      			$("#shareTimeTwo").val(o.shareTimeTwo)
		      			$("#omnHorElectric").val(o.omnHorElectric)
		      			$("#omnHorWater").val(o.omnHorWater)
		      			$("#perSecondPrice").val(o.perSecondPrice)
		      			$("#omnHorHouse").val(o.omnHorHouse)
		      			$("#omnHorMachinist").val(o.omnHorMachinist)
		      			$("#perSecondMachinist").val(o.perSecondMachinist)
		      			$("#managePrice").val(o.managePrice)
		      			$("#perSecondManage").val(o.perSecondManage)
		      			$("#manageEquipmentNumber").val(o.manageEquipmentNumber)
		      			$("#equipmentProfit").val(o.equipmentProfit)
		      			 }); 
		      		  },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
				  $(".updateord").on('click',function(){
					  var data={
					  	id:$("#ordid").val(),
					  	peripheralLaser:$("#peripheralLaser").val(),
					  	extent:$("#extent").val(),
					  	pauseTime:$("#pauseTime").val(),
					  	rabbTime:$("#rabbTime").val(),
					  	quilt:$("#quilt").val(),
					  	quickWorker:$("#quickWorker").val(),
					  	worth:$("#worth").val(),
					  	shareDay:$("#shareDay").val(),
					  	workTime:$("#workTime").val(),
					  	laserTubePrice:$("#laserTubePrice").val(),
					  	shareTime:$("#shareTime").val(),
					  	maintenanceCharge:$("#maintenanceCharge").val(),
					  	shareTimeTwo:$("#shareTimeTwo").val(),
					  	omnHorElectric:$("#omnHorElectric").val(),
					  	omnHorWater:$("#omnHorWater").val(),
					  	omnHorHouse:$("#omnHorHouse").val(),
					  	omnHorMachinist:$("#omnHorMachinist").val(),
					  	managePrice:$("#managePrice").val(),
					  	manageEquipmentNumber:$("#manageEquipmentNumber").val(),
					  	equipmentProfit:$("#equipmentProfit").val(),
					  }
					  $.ajax({
					      url:"${ctx}/product/updatePrimeCoefficient",
					      data:data,
					      type:"POST",
			      		  success: function (result) {
			      			 $(result.data).each(function(i,o){
			      				$("#time").val(o.time)
			      				$("#depreciation").val(o.depreciation)
			      				$("#laserTubePriceSecond").val(o.laserTubePriceSecond)
			      				$("#maintenanceChargeSecond").val(o.maintenanceChargeSecond)
			      				$("#perSecondPrice").val(o.perSecondPrice)
			      				$("#perSecondMachinist").val(o.perSecondMachinist)
			      				$("#perSecondManage").val(o.perSecondManage)
			      				if(0==result.code){
									layer.msg(result.message, {icon: 1});
									layer.close(index);
									}else{
										layer.msg(result.message, {icon: 2});
										layer.close(index);
									}
			      			}); 
			      		  },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);sa
						  }
					  });
				  })
				  //遍历裁剪方式
				  var data = {
							type:"tailor",
						}
						var index;
					    var html = '';
					    var htmlto= '';
					    $.ajax({
						      url:"${ctx}/product/getBaseOne",
						      data:data,
						      type:"GET",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      				html +='<option value="'+o.id+'">'+o.name+'</option>'
				      			}); 
						       htmlto='<select class="text-center form-control selecttailorType2" disabled="disabled"><option value="">请选择</option>'+html+'</select>'
				      		  $(".tailorType2").html(htmlto)
				      		  //改变事件
				      		  $(".selecttailorType2").each(function(i,o){
				      				var id=	$(o).parent().data("tailortypeid2");
									$(o).val(id)
								}) 
				      		  },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);sa
							  }
						  });
					    $(".singleDouble2").each(function(j,k){
					      	var id=$(k).data("singledouble")
							$(k).val(id)
					}) 
					    $('.perimeter2').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		perimeter:$(this).val(),
					    		stallPoint:$(this).parent().parent().find(".stallPoint2").val(),
					    		singleDouble:$(this).parent().parent().find(".singleDouble2").val(),
					    		time:$(this).parent().parent().find(".time2").val(),
					    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne2").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo2").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime2").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime2").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime2").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost2").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice2").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff2").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice2").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.stallPoint2').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		perimeter:$(this).parent().parent().find(".perimeter2").val(),
					    		stallPoint:$(this).parent().parent().find(".stallPoint2").val(),
					    		singleDouble:$(this).parent().parent().find(".singleDouble2").val(),
					    		time:$(this).parent().parent().find(".time2").val(),
					    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne2").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo2").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime2").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime2").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime2").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost2").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice2").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff2").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice2").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.singleDouble2').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		perimeter:$(this).parent().parent().find(".perimeter2").val(),
					    		stallPoint:$(this).parent().parent().find(".stallPoint2").val(),
					    		singleDouble:$(this).parent().parent().find(".singleDouble2").val(),
					    		time:$(this).parent().parent().find(".time2").val(),
					    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne2").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo2").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime2").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime2").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime2").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost2").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice2").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff2").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice2").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.time2').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		perimeter:$(this).parent().parent().find(".perimeter2").val(),
					    		stallPoint:$(this).parent().parent().find(".stallPoint2").val(),
					    		singleDouble:$(this).parent().parent().find(".singleDouble2").val(),
					    		time:$(this).parent().parent().find(".time2").val(),
					    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne2").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo2").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime2").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime2").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime2").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost2").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice2").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff2").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice2").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeOne2').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		perimeter:$(this).parent().parent().find(".perimeter2").val(),
					    		stallPoint:$(this).parent().parent().find(".stallPoint2").val(),
					    		singleDouble:$(this).parent().parent().find(".singleDouble2").val(),
					    		time:$(this).parent().parent().find(".time2").val(),
					    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne2").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo2").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime2").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime2").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime2").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost2").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice2").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff2").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice2").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
					    
					    $('.otherTimeTwo2').blur(function(){
					    	var id=$(this).data("id");
					    	var that=$(this);
					    	var data={
					    		id:id,
					    		perimeter:$(this).parent().parent().find(".perimeter2").val(),
					    		stallPoint:$(this).parent().parent().find(".stallPoint2").val(),
					    		singleDouble:$(this).parent().parent().find(".singleDouble2").val(),
					    		time:$(this).parent().parent().find(".time2").val(),
					    		otherTimeOne:$(this).parent().parent().find(".otherTimeOne2").val(),
					    		otherTimeTwo:$(this).parent().parent().find(".otherTimeTwo2").val(),
					    	}
					    	$.ajax({
							      url:"${ctx}/product/addOrdinaryLaser",
							      data:data,
							      type:"POST",
					      		  success: function (result) {
					      			 $(result.data).each(function(i,o){
					      				that.parent().parent().find(".rabbTime2").text(parseFloat((o.rabbTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserTime2").text(parseFloat((o.singleLaserTime).toFixed(5)));
					      				that.parent().parent().find(".singleLaserHandTime2").text(parseFloat((o.singleLaserHandTime).toFixed(5)));
					      				that.parent().parent().find(".labourCost2").text(parseFloat((o.labourCost).toFixed(8)));
					      				that.parent().parent().find(".equipmentPrice2").text(parseFloat((o.equipmentPrice).toFixed(5)));
					      				that.parent().parent().find(".administrativeAtaff2").text(parseFloat((o.administrativeAtaff).toFixed(5)));
					      				that.parent().parent().find(".stallPrice2").text(parseFloat((o.stallPrice).toFixed(5)));
					      			}); 
					      		  },error:function(){
										layer.msg("加载失败！", {icon: 2});
										layer.close(index);
								  }
							  });
					    })
			  }
			this.loadEvents = function(){
				//遍历裁剪方式
				var data = {
						type:"tailor",
					}
					var index;
				    var html = '';
				    var htmlto= '';
				    $.ajax({
					      url:"${ctx}/product/getBaseOne",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			 $(result.data).each(function(i,o){
			      				html +='<option value="'+o.id+'">'+o.name+'</option>'
			      			}); 
					       htmlto='<select class="text-center form-control selecttailorType"><option value="">请选择</option>'+html+'</select>'
			      		  $(".tailorType").html(htmlto)
			      		  //改变事件
			      		  $(".selecttailorType").each(function(i,o){
			      		var id=	$(o).parent().data("tailortypeid");
								$(o).val(id)
								}) 
			      		  $(".selecttailorType").change(function(){
			      			var that=$(this);
			      			var a=$(this).parent().prev().find(".tailorSize").val();
			      			var id=$(this).parent().prevAll().find(".checkboxId").val();
			      			  if(a==""){
			      				$(that).each(function(i,o){
									$(o).val("")
									}) 
			      				  return layer.msg("请先填写裁片的平方M", {icon: 2});
			      			  }
				    var	datae={
				    			tailorSize:a,
				    			tailorTypeId:$(this).val(),
				    			id:id,
				    	}
				    	$.ajax({
						      url:"${ctx}/product/getOrdinaryLaserDate",
						      data:datae,
						      type:"POST",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      			that.parent().parent().find(".managePrice").text(o.managePrice);
				      			that.parent().parent().find(".experimentPrice").text(parseFloat((o.experimentPrice*1).toFixed(5)));
				      			 }); 
				      			layer.close(index)
				      			var datar={
				      				id:id,
				      				tailorSize:a,
					    			tailorTypeId:that.val(),
					    			managePrice:result.data.managePrice,
					    			experimentPrice:result.data.experimentPrice,
					    			ordinaryLaserId:that.val(),
				      			}
				      			$.ajax({
								      url:"${ctx}/product/addTailor",
								      data:datar,
								      type:"POST",
						      		  success: function (result) {
						      			 $(result.data).each(function(i,o){
						      				if(0==result.code){
												layer.msg(result.message, {icon: 1});
												layer.close(index);
												}else{
													layer.msg(result.message, {icon: 2});
													layer.close(index);
												}
						      			 }); 
						      			layer.close(index)
						      			
						      			
								      },error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(index);
									  }
								  });
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				    		})
			      		  },error:function(){
								layer.msg("加载失败！", {icon: 2});
								layer.close(index);sa
						  }
					  });
				    
				    $(".costPrice").change(function(){
				    	var that=$(this);
		      			var a=$(this).parent().parent().find(".tailorSize").val();
		      			var id=$(this).parent().parent().find(".checkboxId").val();
		      			var selecttailorTypeid=$(this).parent().parent().find(".selecttailorType").val();
		      			var	datae={
				    			tailorSize:a,
				    			tailorTypeId:selecttailorTypeid,
				    			id:id,
				    			costPrice:$(this).val(),
				    	}
				    	$.ajax({
						      url:"${ctx}/product/getOrdinaryLaserDate",
						      data:datae,
						      type:"POST",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      			 that.parent().parent().find(".ratePrice").text(parseFloat((o.ratePrice).toFixed(5)));
				      			 that.parent().parent().find(".allCostPrice").text(parseFloat((o.allCostPrice).toFixed(3)));  
				      			that.parent().parent().find(".scaleMaterial").text(parseFloat((o.scaleMaterial).toFixed(5)));  
				      			that.parent().parent().find(".noeMbroiderPriceDown").text(parseFloat((o.noeMbroiderPriceDown).toFixed(5)));
				      			 }); 
				      			layer.close(index)
				      			var datar={
				      				id:id,
				      				ratePrice:result.data.ratePrice,
				      				allCostPrice:result.data.allCostPrice,
				      				scaleMaterial:result.data.scaleMaterial,
				      				costPrice:that.val(),
				      			}
				      			$.ajax({
								      url:"${ctx}/product/addTailor",
								      data:datar,
								      type:"POST",
						      		  success: function (result) {
						      			 $(result.data).each(function(i,o){
						      				if(0==result.code){
						      					
												layer.msg(result.message, {icon: 1});
												layer.close(index);
												}else{
													layer.msg(result.message, {icon: 2});
													layer.close(index);
												}
						      			 }); 
						      			layer.close(index)
						      			
						      			
								      },error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(index);
									  }
								  });
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
				    })
				   
				    $(".tailorSize").blur(function(){
			      			var that=$(this);
			      			var a=$(this).parent().parent().find(".tailorSize").val();
			      			var id=$(this).parent().parent().find(".checkboxId").val();
			      			var selecttailorTypeid=$(this).parent().parent().find(".selecttailorType").val();
				    if(selecttailorTypeid==""){
				    	return "";
				    }
			      			var	datae={
				    			tailorSize:a,
				    			tailorTypeId:selecttailorTypeid,
				    			id:id,
				    	}
				    	$.ajax({
						      url:"${ctx}/product/getOrdinaryLaserDate",
						      data:datae,
						      type:"POST",
				      		  success: function (result) {
				      			 $(result.data).each(function(i,o){
				      			that.parent().parent().find(".managePrice").text(o.managePrice);
				      			that.parent().parent().find(".experimentPrice").text(parseFloat((o.experimentPrice).toFixed(5)));
				      			that.parent().parent().find(".selectcostprice").html("<select class='form-control costPrice2'><option value='0'>请选择</option><option value="+o.managePrice+">"+o.managePrice+"</option><option value="+o.experimentPrice+">"+o.experimentPrice+"</option></select>")
				      			 }); 
				      			layer.close(index)
				      			var experimentPrice=result.data.experimentPrice;
				      			var managePrice=result.data.managePrice;
				      			var datar={
				      				id:id,
				      				tailorSize:a,
					    			tailorTypeId:selecttailorTypeid,
					    			managePrice:result.data.managePrice,
					    			experimentPrice:result.data.experimentPrice,
					    			ordinaryLaserId:selecttailorTypeid,
				      			}
				      			$.ajax({
								      url:"${ctx}/product/addTailor",
								      data:datar,
								      type:"POST",
						      		  success: function (result) {
						      			 $(result.data).each(function(i,o){
						      				if(0==result.code){
						      					
												layer.msg(result.message, {icon: 1});
												layer.close(index);
												}else{
													layer.msg(result.message, {icon: 2});
													layer.close(index);
												}
						      			 }); 
						      			layer.close(index)
						      			
						      			
								      },error:function(){
											layer.msg("加载失败！", {icon: 2});
											layer.close(index);
									  }
								  });
				      			
				      			$(".costPrice2").change(function(){
				      				var that=$(this);
					      			var a=$(this).parent().parent().find(".tailorSize").val();
					      			var id=$(this).parent().parent().find(".checkboxId").val();
					      			var selecttailorTypeid=$(this).parent().parent().find(".selecttailorType").val();
					      			var	datae={
							    			tailorSize:a,
							    			tailorTypeId:selecttailorTypeid,
							    			id:id,
							    			costPrice:$(this).val(),
							    	}
							    	$.ajax({
									      url:"${ctx}/product/getOrdinaryLaserDate",
									      data:datae,
									      type:"POST",
							      		  success: function (result) {
							      			 $(result.data).each(function(i,o){
							      			 that.parent().parent().find(".ratePrice").text(parseFloat((o.ratePrice).toFixed(5)));
							      			 that.parent().parent().find(".allCostPrice").text(parseFloat((o.allCostPrice).toFixed(3)));  
							      			that.parent().parent().find(".scaleMaterial").text(parseFloat((o.scaleMaterial).toFixed(5)));  
							      			that.parent().parent().find(".noeMbroiderPriceDown").text(parseFloat((o.noeMbroiderPriceDown).toFixed(5)));
							      			 }); 
							      			layer.close(index)
							      			var datar={
							      				id:id,
							      				ratePrice:result.data.ratePrice,
							      				allCostPrice:result.data.allCostPrice,
							      				scaleMaterial:result.data.scaleMaterial,
							      				costPrice:that.val(),
							      			}
							      			$.ajax({
											      url:"${ctx}/product/addTailor",
											      data:datar,
											      type:"POST",
									      		  success: function (result) {
									      			 $(result.data).each(function(i,o){
									      				if(0==result.code){
									      					
															layer.msg(result.message, {icon: 1});
															layer.close(index);
															}else{
																layer.msg(result.message, {icon: 2});
																layer.close(index);
															}
									      			 }); 
									      			layer.close(index)
									      			
									      			
											      },error:function(){
														layer.msg("加载失败！", {icon: 2});
														layer.close(index);
												  }
											  });
									      },error:function(){
												layer.msg("加载失败！", {icon: 2});
												layer.close(index);
										  }
									  });
				      			})
						      },error:function(){
									layer.msg("加载失败！", {icon: 2});
									layer.close(index);
							  }
						  });
			      			
				    		})
				    
			}
			this.events = function(){
				$(".home1").on('click',function(){
					var data={
							page:1,
					  		size:100,	
					  		productId:productIdAll,
					}
					self.loadPagination(data);
				})
				
				$('.searchtaskks').on('click',function(){
					var data = {
				  			page:1,
				  			size:100,
				  			productId:self.getCache(),
				  	}
		            self.loadPagination(data);
				});
				
				$(".searchtask").on('click',function(){
					$(".layer-right").css("display","block");
					var demo = new mSlider({
						dom:".layer-right",
						direction: "right",
						distance:"35%",
						
					})
					demo.open()
				})
				$(".searchtask2").on('click',function(){
					$(".layer-right2").css("display","block");
					var demo = new mSlider({
						dom:".layer-right2",
						direction: "right",
						distance:"35%",
						
					})
					demo.open()
				})
				$(".searchtask3").on('click',function(){
					$(".layer-right3").css("display","block");
					var demo = new mSlider({
						dom:".layer-right3",
						direction: "right",
						distance:"35%",
						
					})
					demo.open()
				})
				
				$(".searchtask4").on('click',function(){
					$(".layer-right4").css("display","block");
					var demo = new mSlider({
						dom:".layer-right4",
						direction: "right",
						distance:"35%",
						
					})
					demo.open()
				})
				$(".searchtask5").on('click',function(){
					$(".layer-right5").css("display","block");
					var demo = new mSlider({
						dom:".layer-right5",
						direction: "right",
						distance:"35%",
						
					})
					demo.open()
				})
				
				$(".searchtask6").on('click',function(){
					$(".layer-right6").css("display","block");
					var demo = new mSlider({
						dom:".layer-right6",
						direction: "right",
						distance:"35%",
						
					})
					demo.open()
				})
				
				//提示产品名
				$("#productName").typeahead({
					//ajax 拿way数据
					source : function(query, process) {
							return $.ajax({
								url : '${ctx}/getProductPages',
								type : 'GET',
								data : {
									name:query,
								},
								
								success : function(result) {
									//转换成 json集合
									 var resultList = result.data.rows.map(function (item) {
										 	//转换成 json对象
					                        var aItem = {name: item.name, id:item.id, number:item.primeCost==null ? "" : item.primeCost.number}
					                        //处理 json对象为字符串
					                        return JSON.stringify(aItem);
					                    });
									//提示框返回数据
									 return process(resultList);
								},
							})
							//提示框显示
						}, highlighter: function (item) {
						    //转出成json对象
							 var item = JSON.parse(item);
							return item.name+"-"+item.id
							//按条件匹配输出
		                }, matcher: function (item) {
		                	//转出成json对象
					        var item = JSON.parse(item);
					        self.setCache(item.id)
					        $('#number').val(item.number)
					    	return item.name
					    },
						//item是选中的数据
						updater:function(item){
							//转出成json对象
							var item = JSON.parse(item);
							self.setCache(item.id)
							 $('#number').val(item.number)
								return item.name
						},
						
					});
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>
  
       
</body>

</html>