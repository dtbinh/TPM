#include "wave.hpp"

int main(int argc, char const *argv[])
{
	unsigned char* data8;
    long int data_nb;
    short channels_nb = 1;
    int sampling_freq;

	Wave wave = new Wave(data8, data_nb, channels_nb, sampling_freq);	
	return 0;
}