jruby-maven-plugin
==================

A maven plugin for invoking JRuby

Configuration
----------------------------------------------------
In order to use this Maven plugin, you will need to configure your pom.xml (or proxy repository) to point to the repository at <http://timezra.github.com/maven/releases>

<code lang="xml">
&nbsp;&nbsp;&nbsp;&nbsp;&lt;pluginRepositories&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;pluginRepository&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;id&gt;tims-repo&lt;/id&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;url&gt;http://timezra.github.com/maven/releases &lt;/url&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;releases&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;enabled&gt;true&lt;/enabled&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/releases&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;snapshots&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;enabled&gt;false&lt;/enabled&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/snapshots&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/pluginRepository&gt;  
&nbsp;&nbsp;&nbsp;&nbsp;&lt;/pluginRepositories&gt;
</code>

Usage
----------------------------------------------------
Work in progress....

For now, please use 'mvn timezra.maven:jruby-maven-plugin:1.7.4:help' to find out usage information.

### Examples: ###

    $ mvn timezra.maven:jruby-maven-plugin:1.7.4:gem-install -Dgem_home=`pwd`/target/gems -Dgem=bundler -e
    $ mvn timezra.maven:jruby-maven-plugin:1.7.4:gem-list -Dgem_home=`pwd`/target/gems -e
    $ mvn timezra.maven:jruby-maven-plugin:1.7.4:bundle-init -Dgem_home=`pwd`/target/gems -e
    $ mvn timezra.maven:jruby-maven-plugin:1.7.4:bundle-install -Dgem_home=`pwd`/target/gems -Dgemfile=`pwd`/Gemfile -e
    $ mvn timezra.maven:jruby-maven-plugin:1.7.4:cli -Druby_version=2.0 -Dargs="-v"
    $ mvn timezra.maven:jruby-maven-plugin:1.7.4:cli -Dgem_home="`pwd`/target/gems" -Dargs="-S \"`pwd`/target/gems/bin/bundle\" show bundler"
    