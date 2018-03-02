<!DOCTYPE html>
<html>
<head>
<title>${comments}</title>
<#noparse>
<#include "/newcommon/header.html">
</#noparse>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox ">
                	 <div class="ibox-content">
                	 	<div class="jqGrid_wrapper">
                            <table id="jqGrid"></table>
                            <div id="jqGridPager"></div>
                        </div>
                	 </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script src="<#noparse>${base}</#noparse>/static/system/${pathName}list.js?_<#noparse>${dateTime}</#noparse>"></script>