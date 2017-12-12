<#list data as item>
 告警级别: ${item.level_des}
 告警指标: ${item.indicator_des}
 运维负责人: ${item.ops_principal}
 告警状态: ${item.status_des}
 业务名称: 
 ${item.module}
 告警时间: 
 ${item.create_time}
 
</#list>

查看 <a href="http://112.65.23.133:7654/wwxweb/#/AlarmList">告警列表</a>