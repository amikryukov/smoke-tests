package group.id.validator;

import com.griddynamics.jagger.coordinator.NodeContext;
import com.griddynamics.jagger.engine.e1.collector.ResponseValidator;
import com.griddynamics.jagger.exception.TechnicalException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class ResponseFromFileValidator<Q, E, R> extends ResponseValidator<Q, E, R> {


    private String filePath= "suite/validatorResources/response.txt";
    private String expectedResponse;

    public ResponseFromFileValidator(String taskId, String sessionId, NodeContext kernelContext) {
        super(taskId, sessionId, kernelContext);
    }

    @Override
    public String getName() {
        return "ResponseFromFileValidator";
    }

    private void initiate(){
        FileInputStream stream = null;
        try {
            stream= new FileInputStream(new File(filePath));
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            expectedResponse=Charset.defaultCharset().decode(bb).toString();
        } catch (IOException e) {
            throw new TechnicalException("Error during read file",e);
        } finally {
            if(stream!=null){
                try {
                    stream.close();
                } catch (IOException e) {

                }
            }
        }
    }

    @Override
    public boolean validate(Q query, E endpoint, R result, long duration) {
        if(expectedResponse==null){
            synchronized (filePath){
                if(expectedResponse==null){
                    initiate();
                }
            }
        }
        return expectedResponse.equals(result);
    }
}
