package com.study.java.lambda.utils;

import com.study.java.lambda.dto.GenericRespDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Log4j2
public class AsyncUtils {
    private AsyncUtils() {}

    public static  Map<String, GenericRespDto> callAndReturn(List<Callable<Object>> taskList){

        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

        Map<String, GenericRespDto> genericResults = new HashMap<>();

        try {

            List<Future<Object>> futures = executorService.invokeAll(taskList);

            for (Future<Object> future : futures) {
                Future<GenericRespDto> preResult = (Future<GenericRespDto>) future.get();
                GenericRespDto result = preResult.get();
                genericResults.put(result.getRequestName(), result);
            }

        } catch (InterruptedException | ExecutionException e){
            genericResults.putAll(createExceptionRespose("async_calling_erro", e));
            log.warn("Failled to run assync {} ", e.getMessage());
            Thread.currentThread().interrupt();

        }finally {
            Thread.interrupted();
            executorService.shutdown();
        }

        return genericResults;
    }

    public static Map<String, GenericRespDto> createExceptionRespose(String callName, Exception body)
    {
        //executa chamada e retorna resultado
        Map<String, GenericRespDto> results = new HashMap<>();
        GenericRespDto errorRespose = new GenericRespDto();
        errorRespose.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorRespose.setResponseBody(body);
        errorRespose.setResponseType(Exception.class);
        results.put(callName, errorRespose);
        return results;
    }


}
