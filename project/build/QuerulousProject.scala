import sbt._
import com.twitter.sbt._

class QuerulousProject(info: ProjectInfo)
  extends StandardParentProject(info)
  with DefaultRepos
  with SubversionPublisher
{

  lazy val coreProject = project(
    "querulous-core", "querulous-core_2.9.1",
    new CoreProject(_))

  /**
   * finagle compatible tracing for database requests
   */
  lazy val tracingProject = project(
    "querulous-tracing", "querulous-tracing_2.9.1",
    new TracingProject(_), coreProject)

  /**
   * Ostrich stats provider for query stats collection
   */
  lazy val ostrich4Project = project(
    "querulous-ostrich4", "querulous-ostrich4_2.9.1",
    new Ostrich4Project(_), coreProject)

  trait Defaults
    extends ProjectDependencies
    with DefaultRepos
    with SubversionPublisher

  class CoreProject(info: ProjectInfo) extends StandardLibraryProject(info) with Defaults {
    val utilCore  = "com.twitter"  % "util-core_2.9.1"      % "3.0.0"
    val dbcp      = "commons-dbcp" % "commons-dbcp"         % "1.4"
    val mysqljdbc = "mysql"        % "mysql-connector-java" % "5.1.18"
    val pool      = "commons-pool" % "commons-pool"         % "1.5.4"

    val utilEval   = "com.twitter"             % "util-eval_2.9.1"    % "3.0.0"     % "test"
    val scalaTools = "org.scala-lang"          % "scala-compiler"     % "2.9.1"     % "test"
    val hamcrest   = "org.hamcrest"            % "hamcrest-all"       % "1.1"       % "test"
    val specs      = "org.scala-tools.testing" % "specs_2.9.1"        % "1.6.9"     % "test"
    val objenesis  = "org.objenesis"           % "objenesis"          % "1.1"       % "test"
    val jmock      = "org.jmock"               % "jmock"              % "2.4.0"     % "test"
    val cglib      = "cglib"                   % "cglib"              % "2.2"       % "test"
    val asm        = "asm"                     % "asm"                % "1.5.3"     % "test"
    val dbcpTests  = "commons-dbcp"            % "commons-dbcp-tests" % "1.4"       % "test"
  }

  class TracingProject(info: ProjectInfo) extends StandardLibraryProject(info) with Defaults {
    val finagleCore   = "com.twitter"        % "finagle-core_2.9.1"    % "3.0.0"
  }

  class Ostrich4Project(info: ProjectInfo) extends StandardLibraryProject(info) with Defaults {
    val finagleOstrich = "com.twitter"       % "finagle-ostrich4_2.9.1"    % "3.0.0"
  }

  override def subversionRepository = Some("https://svn.twitter.biz/maven-public/")

  override def managedStyle = ManagedStyle.Maven
  val publishTo = Resolver.file("dotM2", new java.io.File(Path.userHome + "/.m2/repository"))
}
