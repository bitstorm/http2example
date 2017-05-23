package org.wicket;

import java.util.Arrays;

import org.apache.wicket.Application;
import org.apache.wicket.http2.markup.head.PushHeaderItem;
import org.apache.wicket.http2.markup.head.PushItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.ResourceReference;

public class HomePage extends WebPage 
{
	private static final long serialVersionUID = 1L;

	private Response webPageResponse;

	private Request webPageRequest;

	public HomePage()
	{
		webPageResponse = getRequestCycle().getResponse();
		webPageRequest = getRequestCycle().getRequest();
		add(new Label("label", "Hello"));
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		Application application = Application.get();
		ResourceReference reference = application.getJavaScriptLibrarySettings().
        		getJQueryReference();

		response.render(JavaScriptHeaderItem.forReference(reference));
		response.render(new PushHeaderItem(this, webPageRequest, webPageResponse)
		    .push(Arrays.asList(new PushItem(reference))));
	}

	@Override
	protected void setHeaders(WebResponse response)
	{
		// NOOP just disable caching
	}
}
