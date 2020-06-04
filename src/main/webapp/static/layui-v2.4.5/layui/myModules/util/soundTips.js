var STY = `

`;
layui.define(['jquery','layer','table','myutil',],function(exports){
	var $ = layui.jquery
	,layer = layui.layer
	,myutil = layui.myutil
	,form = layui.form
	,laytpl = layui.laytpl
	,table = layui.table;
	
	var BASE_URL = '/upload/sound/';
	
	var NO_FIND = 'no_find',OUT_SUCCESS = 'out_success',SCAN_SUCCESS = 'scan_success',SYS_ERROR = 'sys_error',
		EXT = '.mp3';
	
	$('head style').append(STY);
	var isNull = "---";
	var soundTips = {
			
	};
	
	soundTips.outSuccess = function(){
		soundTips.play(OUT_SUCCESS);
	}
	soundTips.noFind = function(){
		soundTips.play(NO_FIND);
	}
	soundTips.scanSuccess = function(){
		soundTips.play(SCAN_SUCCESS);
	}
	soundTips.error = function(){
		soundTips.error(SYS_ERROR);
	}
	soundTips.play = function(name){
		var src = BASE_URL+name+EXT;
        var borswer = window.navigator.userAgent.toLowerCase();
        if(borswer.indexOf('ie') >= 0) {
            var strEmbed = '<embed name="embedPlay" src="'+src+'" autostart="true" hidden="true" loop="false" />';
            if($('body').find('embed').length <= 0) {
                $('body').append(strEmbed);
            }else
            	$('body').find('embed').attr('src',src);
            var embed = document.embedPlay;
            //浏览器不支持audio，则使用embed播放
            embed.volume = 100;
        } else {
            //非IE内核浏览器
            var strAudio = '<audio id="audioPlay" src="'+src+'" hidden="true" />';
            if($('body').find('audio').length <= 0) {
                $('body').append(strAudio);
            }else
            	$('body').find('audio').attr('src',src);
            var audio = $('#audioPlay');
        	audio[0].play();
        }
	}
	
	exports('soundTips',soundTips)
})