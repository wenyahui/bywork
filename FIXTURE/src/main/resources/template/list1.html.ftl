<#noparse>
<#include "/common/layout.html">
<@header>
</@header>
</#noparse>
<script src="<#noparse>${base}</#noparse>/assets/system/generator/${pathName}.js?_<#noparse>${date.systemTime}</#noparse>"></script>
<#noparse>
<@body>
</#noparse>
<div class="tpl-content-wrapper" >
            <ol class="am-breadcrumb" >
                <li><a href="#" class="am-icon-home">查询</a></li>
            </ol>
             <div class="tpl-portlet-components">
	             <div v-show="showList">
					<div class="grid-btn">
					   
        	            <#noparse><#if permissions?seq_contains('</#noparse>${pathName}':add)>
		             	<a class="btn btn-primary" ><i class="fa fa-plus"></i>&nbsp;新增</a>
		             	<#noparse></#if></#noparse>
		             	<#noparse><#if permissions?seq_contains('</#noparse>${pathName}':update)>
						<a class="btn btn-primary" ><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
						<#noparse></#if></#noparse>
		             	<#noparse><#if permissions?seq_contains('</#noparse>${pathName}':del)>
						<a class="btn btn-primary"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
						<#noparse></#if></#noparse>
	             	</div>
	             </div>
	             <div class="tpl-block">
                      <div class="am-g">
	                        <div class="am-u-sm-12">
	                            <form class="am-form">
	                            	<table class="am-table am-table-striped am-table-hover table-main" id="jqGrid"></table>
	                            </form>
	                        </div>
	                        <div id="jqGridPager"></div>
                        </div> 
                </div>
	         </div>
	         
</div>
<#noparse>
</@body>
<@footer>
</@footer>
</#noparse>