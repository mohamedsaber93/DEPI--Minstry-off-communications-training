import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:weather_app/cubets/wether_state.dart';
import 'package:weather_app/models/weather_model.dart';
import 'package:weather_app/services/weather_service.dart';

class GetWethearCubit extends Cubit<WeatherDataState> {
  GetWethearCubit() : super(WeatherInitialState());
  WeatherModel? weatherModel;

  getweather({required String cityName}) async {
    try {
      WeatherModel weatherModel =
          await WeatherService(Dio()).getWeatherData(cityName: cityName);
      emit(WethearLoadedState(weatherModel: weatherModel));
    } catch (e) {
      emit(LoadedFailurState());
    }
  }
}
