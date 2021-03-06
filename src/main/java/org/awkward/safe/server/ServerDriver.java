package org.awkward.safe.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 27.07.14
 */
@Component
public class ServerDriver
{
    public static void main(String[] args)
    {
        new ClassPathXmlApplicationContext("classpath:spring.xml").getBean(ServerDriver.class).go(args);
    }

    private void go(String[] args)
    {
        new LocalServer().start();
    }
}
