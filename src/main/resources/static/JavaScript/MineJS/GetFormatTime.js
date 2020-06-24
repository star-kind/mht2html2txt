/**
 * 
 * @returns
 */
function getFormatTime() {
	var date = new Date();
	// 年月日
	var gener = date.getFullYear();
	gener += '-';
	gener += (date.getMonth() + 1);
	gener += '-';
	gener += date.getDate();
	// 时分秒
	gener += ' ';
	gener += date.getHours() + ':';
	gener += date.getMinutes() + ':';
	gener += date.getSeconds();
	return gener;
}
