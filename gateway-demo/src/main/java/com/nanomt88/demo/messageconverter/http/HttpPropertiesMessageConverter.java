package com.nanomt88.demo.messageconverter.http;

import com.nanomt88.demo.messageconverter.dto.Person;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 *  实现自定义http消息描述功能扩展
 * @author nanomt88@gmail.com
 * @create 2019-07-28 10:53
 **/
public class HttpPropertiesMessageConverter extends AbstractHttpMessageConverter<Person> {

    public HttpPropertiesMessageConverter(){
        super( MediaType.valueOf("application/properties+person"));
        setDefaultCharset(Charset.forName("UTF-8"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(clazz);
    }

    @Override
    protected Person readInternal(Class<? extends Person> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        Properties properties = new Properties();
        properties.load(new InputStreamReader(inputMessage.getBody(), getDefaultCharset()));

        Person person = new Person();
        person.setId(Integer.valueOf(properties.getProperty("person.id", "0")));
        person.setName(properties.getProperty("person.name"));
        return person;
    }

    @Override
    protected void writeInternal(Person person, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        Properties properties = new Properties();
        properties.setProperty("person.id" , String.valueOf(person.getId()));
        properties.setProperty("person.name" , person.getName());
        properties.store(new OutputStreamWriter(outputMessage.getBody(),getDefaultCharset()), "writer by web server.");
    }
}
