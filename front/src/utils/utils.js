function isMobile() {
  let userAgentInfo = navigator.userAgent;
  let Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
  for (let v = 0; v < Agents.length; v++) {
    if (userAgentInfo.indexOf(Agents[v]) > 0) {
      return true
    }
  }
  return false;

}
function isEmptyObject(data) {
  if (!data) {
    return true
  }
  for (let i in data) {
    return false
  }
  return true
}
export {isMobile, isEmptyObject}
