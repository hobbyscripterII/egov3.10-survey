<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<style>
body { background-color: #FCEEE9; }
#body { height: auto; }
footer { position: relative; bottom: 0px; }
.div-survey-form-wrap, .div-survey-submit-form-wrap { width: 100%; display: flex; justify-content: center; }
.div-survey-form { width: 900px; height: auto; background-color: white; border-radius: 5px; margin-bottom: 15px; border: 1px solid lightgray; }
.div-question-form { padding: 20px 25px 20px 25px; }
.div-survey-submit-form { width: 900px; display: flex; justify-content: space-between; }
.div-question-title { color: #000; font-weight: bold; }
.div-response-option-form { margin: 10px 0 10px 0; }
.text-response-style { width: 100%; }
.form-control { border: none; border-radius: 0px; border-bottom: 1px solid #dee2e6; }
</style>
<body>
<h1 class="title">설문조사 통계</h1>
<div class="div-survey-form-wrap">
	<div class="div-survey-form">
		<div class="div-question-form">
			<p class="p-survey-text"><c:out value="제목: ${VO.surveyTitle }" /></p>
			<p class="p-survey-text"><c:out value="개요: ${VO.surveyDescript }" /></p>
		</div>
	</div>
</div>
</body>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
<script type="text/javascript">
let isurvey = `${param.isurvey }`;

$.ajax({
    type: 'post',
    url: '/survey/admin/stats-survey.do',
    data: { isurvey : isurvey },
    success: (data) => {
    	let responseList = data.list;
    	
    	console.log('responseList = ', responseList);
    	
    	responseList.forEach(item => {
    		let answerResponseList = item.answerResponseList;
    		let multipleResponseList = item.multipleResponseList;
    		
    		console.log('answerResponseList = ', answerResponseList);
    		console.log('multipleResponseList = ', multipleResponseList);
    		
	    	let questionDiv = $('<div class="div-question-title"></div>');
	    	let optionDiv = $('<div class="div-response-option-form"></div>');
	    	let newDiv = $('<div class="div-survey-form-wrap"></div>');
	    	let newDiv2 = $('<div class="div-survey-form"></div>');
	    	
	    	answerResponseList.forEach(item => {
	    		if(item != null) {
	    			let responseContents = item.responseContents;
		    		let newP = $('<p></p>');
		    		newP.append('✔ ' + responseContents);
		    		optionDiv.append(newP);
	    		}
	    	});
	    	
	    	if(multipleResponseList.length > 1 && answerResponseList.length > 1) {
	    		let newChart = $('<div></div>');
	    		optionDiv.append(newChart);
		    	newDiv2.append(optionDiv);
		    	$(body).append(newDiv);
		    	
                let series = multipleResponseList.map(item => ({
                    name: item.responseOptionName,
                    y: item.responseSum
                }));
                
	    		setTimeout(() => {
                    Highcharts.chart(newChart[0], {
                        chart: { type: 'pie' },
                        title: { text: '' },
                        plotOptions: {
                            series: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                dataLabels: {
                                    enabled: true,
                                    distance: 20,
                                    format: '{point.name} - {point.y}표',
                                    style: {
                                        fontSize: '1.2em',
                                        textOutline: 'none',
                                        opacity: 0.7
                                    },
                                    filter: {
                                        operator: '>',
                                        property: 'percentage',
                                        value: 10
                                    }
                                }
                            }
                        },
                        series: [
                            {
                            	name: '표',
                                colorByPoint: true,
                                data: series
                            }
                        ]
                    });
                }, 0);
	    	}
	    	
	    	questionDiv.append(item.questionName);
	    	newDiv.append(newDiv2);
	    	newDiv2.append(questionDiv);
	    	newDiv2.append(optionDiv);
	    	$(body).append(newDiv);
    	});
    	
    },
    error: (x) => { console.log(x); }
})
</script>
</html>