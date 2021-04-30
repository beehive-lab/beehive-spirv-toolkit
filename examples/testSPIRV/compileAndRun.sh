docker run -it --device /dev/dri:/dev/dri --rm -v $PWD:/example -w /example intel-docker env CPLUS_INCLUDE_PATH=. make
#docker run -it --device /dev/dri:/dev/dri --rm -v $PWD:/example -w /example intel-docker env CPLUS_INCLUDE_PATH=. make run

