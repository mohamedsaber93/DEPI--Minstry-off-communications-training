import 'dart:developer';

import 'package:dio/dio.dart';
import 'package:weather_app/models/weather_model.dart';

class WeatherService {
  final Dio dio;
  final String baseUrl = 'http://api.weatherapi.com/v1';
  final String apiKey = '9b8dcb5982f64d1eadf153158231112';
  WeatherService(this.dio);

  Future<WeatherModel> getWeatherData({required String cityName}) async {
    try {
      Response response = await dio
          .get('$baseUrl/forecast.json?key=$apiKey&q=$cityName&days=1');

      WeatherModel weatherModel = WeatherModel.fromJson(response.data);
      return weatherModel;
    } on DioException catch (e) {
      final String errmessage = e.response?.data['error']['message'] ??
          'there was an error try later';
      throw Exception(errmessage);
    } catch (e) {
      log(e.toString());
      throw Exception('there was an error try later');
    }
  }
}
