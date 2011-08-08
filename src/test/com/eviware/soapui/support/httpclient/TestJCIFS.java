package com.eviware.soapui.support.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import com.eviware.soapui.impl.wsdl.support.http.NTLMSchemeFactory;

public class TestJCIFS
{
	@Test
	public void test() throws ParseException, IOException
	{
		DefaultHttpClient httpClient = new DefaultHttpClient();

		httpClient.getAuthSchemes().register( AuthPolicy.NTLM, new NTLMSchemeFactory() );
		httpClient.getAuthSchemes().register( AuthPolicy.SPNEGO, new NTLMSchemeFactory() );

		NTCredentials creds = new NTCredentials( "testuser", "kebabsalladT357", "", "" );
		httpClient.getCredentialsProvider().setCredentials( AuthScope.ANY, creds );

		HttpHost target = new HttpHost( "192.168.104.10", 81, "http" );
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpget = new HttpGet( "/" );

		HttpResponse response1 = httpClient.execute( target, httpget, localContext );
		HttpEntity entity1 = response1.getEntity();

		//		System.out.println( "----------------------------------------" );
		//System.out.println( response1.getStatusLine() );
		//		System.out.println( "----------------------------------------" );
		if( entity1 != null )
		{
			//System.out.println( EntityUtils.toString( entity1 ) );
		}
		//		System.out.println( "----------------------------------------" );

		// This ensures the connection gets released back to the manager
		EntityUtils.consume( entity1 );

		Assert.assertEquals( response1.getStatusLine().getStatusCode(), 200 );
	}

}
