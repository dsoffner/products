# Spring Boot and Camel REST Demo

This example demonstrates how to use Camel's REST DSL to expose a RESTful API which calls 2 SOAP web services: SapService and StartrackService. It i;;ustrates the use of EIPs including Content-Based Router and Composed Message Processor.

### Building

The example can be built with

    mvn clean install

### Running the example in OpenShift

It is assumed that:
- OpenShift platform is already running, if not you can find details how to [Install OpenShift at your site](https://docs.openshift.com/container-platform/3.3/install_config/index.html).
- Your system is configured for Fabric8 Maven Workflow, if not you can find a [Get Started Guide](https://access.redhat.com/documentation/en/red-hat-jboss-middleware-for-openshift/3/single/red-hat-jboss-fuse-integration-services-20-for-openshift/)

The example can then be built and deployed using a single goal:

    $ mvn fabric8:deploy


To list all the running pods:

    oc get pods

Then find the name of the pod that runs this quickstart, and output the logs from the running pods with:

    oc logs <name of pod>

You can also use the OpenShift [web console](https://docs.openshift.com/container-platform/3.3/getting_started/developers_console.html#developers-console-video) to manage the
running pods, and view logs and much more.

### Running via an S2I Application Template

Application templates allow you deploy applications to OpenShift by filling out a form in the OpenShift console that allows you to adjust deployment parameters.  This template uses an S2I source build so that it handle building and deploying the application for you.

First, import the Fuse image streams:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/fis-image-streams.json

Then create the quickstart template:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/quickstarts/spring-boot-camel-rest-sql-template.json

Now when you use "Add to Project" button in the OpenShift console, you should see a template for this quickstart. 


### Accessing the REST service

When the example is running, a REST service is available to get shipping product info and filtering of responses eg,

  More 
28 of 8,801  
 
Print all In new window
Fwd: AusPost Demo on https://master.openshift.megatron.world 
Inbox
x 

Andy Yuen <ayuen@redhat.com>
4:18 PM (17 hours ago)

to Mike, Jason, Stefano 
Mike,

Could you have a look at this issue? Is it a simple thing to fix? We need the Java Console to show the Camel route diagram in the demo.

FYI, I've seen this happening using the Red Hat Development Suite env (Vagrant) but 'oc cluster up' is ok. Different version?!

Thanks
Andy
---------- Forwarded message ----------
From: Andy Yuen <ayuen@redhat.com>
Date: Mon, Apr 10, 2017 at 11:00 AM
Subject: RE: AusPost Demo on https://master.openshift.megatron.world
To: Jason Bodsworth <jbodswor@redhat.com>, Daniel Soffner <dsoffner@redhat.com>, Kunal Limaye <klimaye@redhat.com>
Cc: Wayne Dovey <wdovey@redhat.com>, Mike Hepburn <mhepburn@redhat.com>, "Yuen, Andy" <ayuen@redhat.com>


Hey Guys,

The demo is all working except when trying to access the Openshift Java Console which gives the following error:
Inline image 1

Thanks to Wayne who suggested deleting and re-creating the FIS image streams. Once I've done that, I can use binary S2I to deploy on megatron.world.

Here are the details:

SapService:
http://sapem-sapem.apps.openshift.megatron.world/service/SapService?wsdl

StartrackService
http://startrack-startrack.apps.openshift.megatron.world/service/StartrackService?wsdl

products REST Service (Daniel, this is the one you need to bring out to 3Scale):
http://products-products.apps.openshift.megatron.world/shipping/v1/products

The REST service calls the abovr SOAP services and aggregates the results as per Auspost use case.

With lots of query parameters eg,
http://{hostname}/shipping/v1/products?product_ids=STR-ON,STR-ARL,EPARCEL-REG&features=ATL,COLLECT&from_postcode=3000&to_postcode=2000&arrival_date=2016-06-30




