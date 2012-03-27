StringQueue
-----------

Simple class to use String as Queues. This class makes working with (simple) serialized content easy.


Example
-------

    //some serialized stuff
    String textToParse ="001one20120301testcase";
    
    //create
    StringQueue queue = new StringQueue(textToParse);
    
    //consume
    queue.pollInteger(3);	// 1
    queue.poll(3); 		// one
    queue.pollDate("yyyyMMdd");	// 2012-03-01 (Date Object)
    queue.pollAll();		// testcase

See net.upsidedownmind.stringutils.test.Example for more exmaples

To Do
-----

* Suport missing methods

Copyright
---------

Copyright (c) 2012 Upside-Down Mind. See LICENSE for details.

