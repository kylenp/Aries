<template>
  	<!--main-container-part-->
	<section>
		<el-col :span="24" class="search-toolbar" >
			<el-form :inline="true"  >
				<el-form-item>
					<el-input auto-complete="off" size="medium" placeholder="uuid" v-model="filters.uuid"  ></el-input>
				</el-form-item>
				<el-form-item>
						<el-button type="primary" v-on:click="getData" >查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>
		<el-col  :offset="0"  style="padding-top: 20px">
			<div id="myChart" style="width: 100%;height: 300px" ></div>
		</el-col>
	</section>
</template>
<script>
import {getConsumeByDay} from "@/api/api";
import {formatDate} from "@/components/view/common/date";
import echarts from 'echarts';
export default {
    name: 'scheduleList',
    data () {
       	return {
       		filters:{
       			uuid:''
       		},
       		chart:{
       			xData:[],
       			yData:[]
       		}
    	}  
    },
    mounted:function(){
    	
    },
    methods:{
    	getData:function(){
    		this.chart.xData = [];
		    this.chart.yData = [];
    		var uuid = this.filters.uuid ;
    		if(!uuid || uuid==''){
    			this.$message({
                    showClose: true,
                    message: '请输入uuid!',
                    type: 'error'
                });
    			return ;
    		}
    		var dateStr = formatDate(new Date(),'yyyyMMdd');
    		getConsumeByDay(uuid,dateStr).then(resp => {
    			var x = [];
    			var y = [];
    			$.each(resp.data,function(index,consume){
		        	x.push(formatDate(new Date(consume.createDate),'hh:mm'));
		        	y.push(consume.actualConsume);	
    			});
		        
		        this.chart.xData = x;
		        this.chart.yData = y;
		        this.getChart();
	        })
    	},
    	getChart:function(){
    		var dateStr = formatDate(new Date(),'yyyyMMdd');
	        // 基于准备好的dom，初始化echarts实例
	        let myChart = echarts.getInstanceByDom(document.getElementById('myChart'))
    		if(!myChart){
    			myChart = echarts.init(document.getElementById('myChart'));
    		}
	        // 绘制图表
	        myChart.setOption({
	        	backgroundColor: new echarts.graphic.RadialGradient(0, 0, 0.8,0),
	            title: { text: '实际曝光量折线('+dateStr+')',x:'center'},
	            tooltip: {},
	            xAxis: {
	                data: this.chart.xData,
	                splitLine: {
			            lineStyle: {
			                type: 'dashed'
			            }
			        }
	            },
	            yAxis: {
	            	splitLine: {
			            lineStyle: {
			                type: 'dashed'
			            }
			        },
			        scale: true
	            },
	            series: [{
	                name: '投放量',
	                type: 'line',
	                data: this.chart.yData
	            }]
	        });
    	}
    }
 }
</script>
<style scoped>
</style>