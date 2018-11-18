use strict;
use URI;

sub print_config {
  my ($config_base, $uri_str, $no_proxy) = @_;

  if ($uri_str ne "") {
    my $uri = URI->new($uri_str);
    ($uri->host ne "") && print($config_base, ".proxyHost=${\$uri->host}\n");
    ($uri->_port ne "") && print($config_base, ".proxyPort=${\$uri->_port}\n");

    my ($user, $pass) = split(':', $uri->userinfo, 2);
    ($user ne "") && print($config_base, ".proxyUser=$user\n");
    ($pass ne "") && print($config_base, ".proxyPassword=$pass\n");
  }

  $no_proxy =~ tr/,/|/;
  ($no_proxy ne "") && print($config_base, ".nonProxyHosts=$no_proxy\n");
}

print_config('systemProp.http', $ENV{http_proxy}, $ENV{no_proxy});
print_config('systemProp.https', $ENV{https_proxy}, $ENV{no_proxy});
