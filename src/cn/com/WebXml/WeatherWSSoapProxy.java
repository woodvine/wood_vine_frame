package cn.com.WebXml;

public class WeatherWSSoapProxy implements cn.com.WebXml.WeatherWSSoap {
  private String _endpoint = null;
  private cn.com.WebXml.WeatherWSSoap weatherWSSoap = null;
  
  public WeatherWSSoapProxy() {
    _initWeatherWSSoapProxy();
  }
  
  public WeatherWSSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWeatherWSSoapProxy();
  }
  
  private void _initWeatherWSSoapProxy() {
    try {
      weatherWSSoap = (new cn.com.WebXml.WeatherWSLocator()).getWeatherWSSoap();
      if (weatherWSSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)weatherWSSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)weatherWSSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (weatherWSSoap != null)
      ((javax.xml.rpc.Stub)weatherWSSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cn.com.WebXml.WeatherWSSoap getWeatherWSSoap() {
    if (weatherWSSoap == null)
      _initWeatherWSSoapProxy();
    return weatherWSSoap;
  }
  
  public cn.com.WebXml.GetRegionDatasetResponseGetRegionDatasetResult getRegionDataset() throws java.rmi.RemoteException{
    if (weatherWSSoap == null)
      _initWeatherWSSoapProxy();
    return weatherWSSoap.getRegionDataset();
  }
  
  public java.lang.String[] getRegionProvince() throws java.rmi.RemoteException{
    if (weatherWSSoap == null)
      _initWeatherWSSoapProxy();
    return weatherWSSoap.getRegionProvince();
  }
  
  public java.lang.String[] getRegionCountry() throws java.rmi.RemoteException{
    if (weatherWSSoap == null)
      _initWeatherWSSoapProxy();
    return weatherWSSoap.getRegionCountry();
  }
  
  public cn.com.WebXml.GetSupportCityDatasetResponseGetSupportCityDatasetResult getSupportCityDataset(java.lang.String theRegionCode) throws java.rmi.RemoteException{
    if (weatherWSSoap == null)
      _initWeatherWSSoapProxy();
    return weatherWSSoap.getSupportCityDataset(theRegionCode);
  }
  
  public java.lang.String[] getSupportCityString(java.lang.String theRegionCode) throws java.rmi.RemoteException{
    if (weatherWSSoap == null)
      _initWeatherWSSoapProxy();
    return weatherWSSoap.getSupportCityString(theRegionCode);
  }
  
  public java.lang.String[] getWeather(java.lang.String theCityCode, java.lang.String theUserID) throws java.rmi.RemoteException{
    if (weatherWSSoap == null)
      _initWeatherWSSoapProxy();
    return weatherWSSoap.getWeather(theCityCode, theUserID);
  }
  
  
}