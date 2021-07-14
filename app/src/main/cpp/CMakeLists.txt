cmake_minimum_required(VERSION 3.10)
project(natives)

set(CMAKE_CXX_STANDARD 14)


find_library( 
ANDROID_LIBS
log)
add_library(natives SHARED Natives.cpp)
target_link_libraries(natives c dl ${ANDROID_LIBS})

