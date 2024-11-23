import 'package:weather_app/models/weather_model.dart';

class WeatherDataState {}

class WeatherInitialState extends WeatherDataState {}

class WethearLoadedState extends WeatherDataState {
  final WeatherModel weatherModel;

  WethearLoadedState({required this.weatherModel});
}

class LoadedFailurState extends WeatherDataState {}
