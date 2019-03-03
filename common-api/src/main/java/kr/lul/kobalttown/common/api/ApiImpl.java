package kr.lul.kobalttown.common.api;

import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;
import static kr.lul.kobalttown.common.util.Arguments.notEmpty;
import static kr.lul.kobalttown.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019-02-27
 */
public class ApiImpl implements Api {
  private Verb verb;
  private String name;
  private Map<String, Class> input;
  private Map<String, Class> output;

  public ApiImpl(Verb verb, String name) {
    this(verb, name, Map.of(), Map.of());
  }

  public ApiImpl(Verb verb, String name, Map<String, Class> input, Map<String, Class> output) {
    notNull(verb, "verb");
    notEmpty(name, "name");
    notNull(input, "input");
    notNull(output, "output");

    this.verb = verb;
    this.name = name;
    this.input = unmodifiableMap(input);
    this.output = unmodifiableMap(output);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.common.api.Api
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Verb verb() {
    return this.verb;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public Map<String, Class> input() {
    return this.input;
  }

  @Override
  public Set<String> inputNames() {
    return this.input.keySet();
  }

  @Override
  public Map<String, Class> output() {
    return this.output;
  }

  @Override
  public Set<String> outputNames() {
    return this.output.keySet();
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return format("%s %s : %s => %s", this.verb, this.name, this.input, this.output);
  }
}