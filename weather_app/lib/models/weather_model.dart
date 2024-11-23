import 'package:flutter/material.dart';

class WeatherModel {
  final String cityName;
  final DateTime date;
  final String? image;
  final double temp;
  final double minTemp;
  final double maxTemp;
  final String condition;

  WeatherModel({
    required this.cityName,
    required this.date,
    this.image,
    required this.temp,
    required this.minTemp,
    required this.maxTemp,
    required this.condition,
  });

  factory WeatherModel.fromJson(json) {
    return WeatherModel(
      cityName: json['location']['name'],
      date: DateTime.parse(json['current']['last_updated']),
      temp: json['forecast']['forecastday'][0]['day']['avgtemp_c'],
      maxTemp: json['forecast']['forecastday'][0]['day']['maxtemp_c'],
      minTemp: json['forecast']['forecastday'][0]['day']['mintemp_c'],
      condition: json['forecast']['forecastday'][0]['day']['condition']['text'],
      image: json['current']['condition']['icon'],
    );
  }

  MaterialColor getWeatherColor(String? condition) {
    if (condition == null) {
      return Colors.blue;
    }
    if (condition == 'Sunny' || condition == 'Clear') {
      return Colors.orange;
    } else if (condition == 'Partly cloudy' || condition == 'Cloudy') {
      return Colors.cyan;
    } else if (condition == 'Overcast' ||
        condition == 'Mist' ||
        condition == 'Fog' ||
        condition == 'Freezing fog') {
      return Colors.grey;
    } else if (condition == 'Patchy rain possible' ||
        condition == 'Patchy light rain' ||
        condition == 'Light rain' ||
        condition == 'Moderate rain at times' ||
        condition == 'Moderate rain' ||
        condition == 'Heavy rain at times' ||
        condition == 'Heavy rain' ||
        condition == 'Light freezing rain' ||
        condition == 'Moderate or heavy freezing rain' ||
        condition == 'Patchy snow possible' ||
        condition == 'Patchy sleet possible' ||
        condition == 'Patchy freezing drizzle possible' ||
        condition == 'Thundery outbreaks possible' ||
        condition == 'Blowing snow' ||
        condition == 'Blizzard' ||
        condition == 'Light drizzle' ||
        condition == 'Freezing drizzle' ||
        condition == 'Heavy freezing drizzle' ||
        condition == 'Moderate or heavy sleet' ||
        condition == 'Patchy moderate snow' ||
        condition == 'Moderate snow' ||
        condition == 'Patchy heavy snow' ||
        condition == 'Heavy snow' ||
        condition == 'Ice pellets' ||
        condition == 'Light rain shower' ||
        condition == 'Moderate or heavy rain shower' ||
        condition == 'Torrential rain shower' ||
        condition == 'Light sleet showers' ||
        condition == 'Moderate or heavy sleet showers' ||
        condition == 'Light snow showers' ||
        condition == 'Moderate or heavy snow showers' ||
        condition == 'Light showers of ice pellets' ||
        condition == 'Moderate or heavy showers of ice pellets') {
      return Colors.lightBlue;
    } else if (condition == 'Patchy sleet possible' ||
        condition == 'Patchy light drizzle' ||
        condition == 'Moderate rain at times' ||
        condition == 'Moderate rain' ||
        condition == 'Moderate or heavy rain' ||
        condition == 'Patchy light snow' ||
        condition == 'Light snow' ||
        condition == 'Patchy moderate snow' ||
        condition == 'Moderate snow' ||
        condition == 'Patchy heavy snow' ||
        condition == 'Heavy snow' ||
        condition == 'Patchy light rain with thunder' ||
        condition == 'Moderate or heavy rain with thunder' ||
        condition == 'Patchy light snow with thunder' ||
        condition == 'Moderate or heavy snow with thunder') {
      return Colors.yellow;
    }
    // Add more concatenated conditions here for the remaining weather conditions

    // Default color if no condition matches
    return Colors.blue;
  }
}
