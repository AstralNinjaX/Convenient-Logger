TimerLogger
==========

The TimerLogger class is designed to make logging easier. It has methods for starting and stopping a timer, logging the results, as well as methods for logging messages or errors.

It can log to any PrintStream provided, with a default of System.out for general output and System.err for errors. It can also tabify logging timers, showing their hierarchy.

The TimerLoggable and StaticTimerLoggable abstract classes can be extended to proved TimerLogger functionality without needing to create a TimerLogger.

I have also provided a JAR file with the Java and Class files, so that you can use that directly instead of the java files themselves.

==========

Copyright 2013 - 2014 Devin Falgoust

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
